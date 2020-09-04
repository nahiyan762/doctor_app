package com.sftelehealth.doctor.app.services;

import android.app.IntentService;
import android.content.Intent;

import com.sftelehealth.doctor.app.utils.ContactsProvider;
import com.sftelehealth.doctor.app.utils.PermissionsHelper;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdateContactIntentService extends IntentService {

    public UpdateContactIntentService() {
        super("UpdateContactIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        PermissionsHelper ph = new PermissionsHelper();
        if(ph.hasContactsPermission(getApplicationContext())) {

            String phone = intent.getStringExtra("phone");
            // set the Doctor 24x7 contact number
            ContactsProvider contactsProvider = new ContactsProvider(this);
            contactsProvider.writeContact(phone);
        }
    }
}
