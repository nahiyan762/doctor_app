package com.sftelehealth.doctor.app.utils;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.sftelehealth.doctor.domain.model.Contact;


public class ContactsProvider {

    private Uri QUERY_URI = ContactsContract.Contacts.CONTENT_URI;
    private String CONTACT_ID = ContactsContract.Contacts._ID;
    private String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    private Uri EMAIL_CONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
    private String EMAIL_CONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
    private String EMAIL_DATA = ContactsContract.CommonDataKinds.Email.DATA;
    private String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
    private String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private Uri PHONE_CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    private String STARRED_CONTACT = ContactsContract.Contacts.STARRED;
    private ContentResolver contentResolver;

    private Context context;
    private Activity activity;

    public ContactsProvider(Context activity) {
        contentResolver = activity.getContentResolver();
        activity = activity;
        context = activity;
    }

    public void getContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        String[] projection = new String[]{CONTACT_ID, DISPLAY_NAME, HAS_PHONE_NUMBER, STARRED_CONTACT};
        String selection = null;
        Cursor cursor = contentResolver.query(QUERY_URI, projection, selection, null, DISPLAY_NAME + " ASC");

        while (cursor.moveToNext()) {
            if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER))) > 0) {
                Contact contact = getContact(cursor);
                Log.d("ContactsProvider", "Name: " + contact.name + " Email: " + contact.email + " Phone: " + contact.phone);
                contactList.add(contact);
            }
        }
        cursor.close();
    }

    private Contact getContact(Cursor cursor) {
        String contactId = cursor.getString(cursor.getColumnIndex(CONTACT_ID));
        String name = (cursor.getString(cursor.getColumnIndex(DISPLAY_NAME)));
        Uri uri = Uri.withAppendedPath(QUERY_URI, String.valueOf(contactId));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        String intentUriString = intent.toUri(0);

        Contact contact = new Contact();
        contact.id = Integer.valueOf(contactId);
        contact.name = name;

        getPhone(cursor, contactId, contact);
        getEmail(contactId, contact);
        return contact;
    }

    private void getEmail(String contactId, Contact contact) {
        Cursor emailCursor = contentResolver.query(EMAIL_CONTENT_URI, null, EMAIL_CONTACT_ID + " = ?", new String[]{contactId}, null);
        while (emailCursor.moveToNext()) {
            String email = emailCursor.getString(emailCursor.getColumnIndex(EMAIL_DATA));
            if (!TextUtils.isEmpty(email)) {
                contact.email = email;
            }
        }
        emailCursor.close();
    }

    private void getPhone(Cursor cursor, String contactId, Contact contact) {
        int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
        if (hasPhoneNumber > 0) {
            Cursor phoneCursor = contentResolver.query(PHONE_CONTENT_URI, null, PHONE_CONTACT_ID + " = ?", new String[]{contactId}, null);
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(PHONE_NUMBER));
                contact.phone = phoneNumber;
            }
            phoneCursor.close();
        }
    }

    public int contactExists(String number) {
        /// number is the phone number
        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                Log.d("ContactsProvider", "name - " + cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)));
                if (cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)).equals(Constant.DOCTOR_CONTACT_NAME))
                    return cur.getInt(cur.getColumnIndex(ContactsContract.PhoneLookup._ID));
                else
                    return -1;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return -1;
    }

    public void writeContact(String doctorContact) {
        int doctor24x7ContactId = contactExists(doctorContact);
        if (doctor24x7ContactId != -1) {
            // update contact
            updateContact(doctorContact, doctor24x7ContactId);

        } else {

            ArrayList<ContentProviderOperation> ops =
                    new ArrayList<ContentProviderOperation>();

            int rawContactID = ops.size();

            // Adding insert operation to operations list
            // to insert a new raw contact in the table ContactsContract.RawContacts
            ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

            // Adding insert operation to operations list
            // to insert display name in the table ContactsContract.Data
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, Constant.DOCTOR_CONTACT_NAME)
                    .build());

            // Adding insert operation to operations list
            // to insert Mobile Number in the table ContactsContract.Data
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    //.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, Constants.DOCTOR_CONTACT_NUMBER)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, doctorContact)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());

            try {
                // Executing all the insert operations as a single database transaction
                ContentProviderResult[] results = contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
                int contactId = Integer.parseInt(results[0].uri.getLastPathSegment());
                //contactSharedPreferences.setContactID(contactId);
                //SharedPreferences.Editor editor = ((BaseAppCompatActivity)activity).getSharedPreferences().edit();
                //editor.putInt(CONTACT_ID,contactId);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            }
        }

        // Adding insert operation to operations list
        // to  insert Home Phone Number in the table ContactsContract.Data
        /*ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, etHomePhone.getText().toString())
                .withValue(Phone.TYPE, Phone.TYPE_HOME)
                .build());*/

        // Adding insert operation to operations list
        // to insert Home Email in the table ContactsContract.Data
        /*ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, etHomeEmail.getText().toString())
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                .build());*/

        // Adding insert operation to operations list
        // to insert Work Email in the table ContactsContract.Data
        /*ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                .withValue(Email.ADDRESS, etWorkEmail.getText().toString())
                .withValue(Email.TYPE, Email.TYPE_WORK)
                .build());*/


    }

    /*public boolean checkContact() {
        if (((BaseAppCompatActivity)activity).getSharedPreferences().getInt(Keys.CONTACT_ID, 0) != 0) {
            return true;
        }

        return false;
    }*/

    public void updateContact(String number, int contactId) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        // Number
        ContentProviderOperation.Builder builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        //builder.withSelection(ContactsContract.Data.CONTACT_ID + " = ?" + " AND " + ContactsContract.Data.MIMETYPE + "=?"+ " AND " + ContactsContract.CommonDataKinds.Organization.TYPE + "=?", new String[]{String.valueOf(contactId), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME)});
        builder.withSelection(ContactsContract.Data.CONTACT_ID + " = ?", new String[]{String.valueOf(contactId)});

        ContentValues cv = new ContentValues();
        cv.put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, Constant.DOCTOR_CONTACT_NAME);
        cv.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
        //builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
        builder.withValues(cv);
        ops.add(builder.build());

        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            Log.e("ContactsProvider", "contact not updated as was already inserted");
        }
    }
}