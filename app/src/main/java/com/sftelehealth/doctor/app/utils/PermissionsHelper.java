package com.sftelehealth.doctor.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


/**
 * Created by rahul on 23/06/16.
 */
public class PermissionsHelper {

    public enum PermissionTypes {
        CAMERA_AND_GALLERY, ACCOUNTS, CONTACTS, READ_WRITE_STORAGE, PERMISSIONS_REQUEST_CAMERA_AUDIO
    }

    public final static int MY_PERMISSIONS_REQUEST = 383;

    public boolean isSmsPermissionGranted(Activity activity) {
        // Here, thisActivity is the current activity

        int receiveSms = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS);
        int readContacts = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS);
        int writeContacts = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CONTACTS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (receiveSms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        }
        if (readContacts != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (writeContacts != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_CONTACTS);
        }

        if (listPermissionsNeeded.size() > 0) {

            // Should we show an explanation?
            /*if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.RECEIVE_SMS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                        MY_PERMISSIONS_REQUEST);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }*/

            ActivityCompat.requestPermissions(activity,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    MY_PERMISSIONS_REQUEST);

            return false;

        } else {

            return true;
        }
    }


    /**
     * Method to check Camera and Storage read/write permission when requesting this from an Activtiy
     * @param context
     * @param returningActivity
     * @param callback
     */
    public void requestCameraPermissions(final Context context, final Activity returningActivity, PermissionCallback callback) {

        // Here, thisActivity is the current activity
        int getCamera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int readExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        final String[] listPermissionsNeeded = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //listPermissionsNeeded.add()

        if (getCamera != PackageManager.PERMISSION_GRANTED
                || readExternalStorage != PackageManager.PERMISSION_GRANTED
                || writeExternalStorage != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(returningActivity,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.i("Permission", "Permission info required for CAMERA and STORAGE");
                new AlertDialog.Builder(context)
                        //.setTitle("Delete entry")
                        .setCancelable(false)
                        .setMessage("Please provide access to your camera and gallery.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                // No explanation needed, we can request the permission.
                                ActivityCompat.requestPermissions(
                                        returningActivity,
                                        listPermissionsNeeded,
                                        Constant.PERMISSIONS_REQUEST_CAMERA);
                            }
                        })
                        .show();
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                        returningActivity,
                        listPermissionsNeeded,
                        Constant.PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

            // No explanation needed, we can request the permission.
            /*ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission_group.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_CAMERA);*/
        } else {
            // perform the action to open camera
            callback.permissionGranted(PermissionTypes.CAMERA_AND_GALLERY);
        }
    }

    /**
     * Method to check Camera and Storage read/write permission when requesting this from a Fragment
     * @param context
     * @param returningActivity
     * @param callback
     * @param fragment
     */
    public void requestCameraPermissions(final Context context, final Activity returningActivity, PermissionCallback callback, final Fragment fragment) {

        // Here, thisActivity is the current activity
        int getCamera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int readExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        final String[] listPermissionsNeeded = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //listPermissionsNeeded

        if (getCamera != PackageManager.PERMISSION_GRANTED
                || readExternalStorage != PackageManager.PERMISSION_GRANTED
                || writeExternalStorage != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(returningActivity,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.i("Permission", "Permission info required for CAMERA and STORAGE");
                new AlertDialog.Builder(context)
                        //.setTitle("Delete entry")
                        .setCancelable(false)
                        .setMessage("Please provide access to your camera and gallery.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                // No explanation needed, we can request the permission.
                                fragment.requestPermissions(
                                        listPermissionsNeeded,  // new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        Constant.PERMISSIONS_REQUEST_CAMERA
                                );


                            }
                        })
                        .show();
            } else {

                // No explanation needed, we can request the permission.
                fragment.requestPermissions(
                        listPermissionsNeeded,  // new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constant.PERMISSIONS_REQUEST_CAMERA
                );

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

            // No explanation needed, we can request the permission.
            /*ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission_group.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_CAMERA);*/
        } else {
            // perform the action to open camera
            callback.permissionGranted(PermissionTypes.CAMERA_AND_GALLERY);
        }
    }

    public boolean hasContactsPermission(Context context) {

        // Here, thisActivity is the current activity
        int readContacts = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
        int writeContacts = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CONTACTS);

        final String[] listPermissionsNeeded = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};

        Log.i("Permission", "Permission to access contacts");
        if (readContacts != PackageManager.PERMISSION_GRANTED
                || writeContacts != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to check Camera and Storage read/write permission when requesting this from an Activtiy
     * @param context
     * @param returningActivity
     * @param callback
     */
    public void requestFileSystemPermissions(final Context context, final Activity returningActivity, PermissionCallback callback) {

        // Here, thisActivity is the current activity
        int readExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        final String[] listPermissionsNeeded = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (readExternalStorage != PackageManager.PERMISSION_GRANTED
                || writeExternalStorage != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(returningActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(returningActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.i("Permission", "Permission info required for STORAGE");
                new AlertDialog.Builder(context)
                        //.setTitle("Delete entry")
                        .setCancelable(false)
                        .setMessage("Please provide access to your phone storage for storing the PDF.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                // No explanation needed, we can request the permission.
                                ActivityCompat.requestPermissions(
                                        returningActivity,
                                        listPermissionsNeeded, // new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        Constant.PERMISSIONS_REQUEST_READ_WRITE_STORAGE);
                            }
                        })
                        .show();
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                        returningActivity,
                        listPermissionsNeeded,  // new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constant.PERMISSIONS_REQUEST_READ_WRITE_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

            // No explanation needed, we can request the permission.
            /*ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission_group.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_CAMERA);*/
        } else {
            // perform the action to open camera
            callback.permissionGranted(PermissionTypes.READ_WRITE_STORAGE);
        }
    }

    /**
     * Method to check Camera and Storage read/write permission when requesting this from a Fragment
     * @param context
     * @param returningActivity
     * @param callback
     * @param fragment
     */
    public void requestFileSystemPermissions(final Context context, final Activity returningActivity, PermissionCallback callback, final Fragment fragment) {

        // Here, thisActivity is the current activity
        int readExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        final String[] listPermissionsNeeded = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //listPermissionsNeeded

        if (readExternalStorage != PackageManager.PERMISSION_GRANTED
                || writeExternalStorage != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(returningActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(returningActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.i("Permission", "Permission info required for STORAGE");
                new AlertDialog.Builder(context)
                        //.setTitle("Delete entry")
                        .setCancelable(false)
                        .setMessage("Please provide access to your phone storage for storing the PDF.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                // No explanation needed, we can request the permission.
                                fragment.requestPermissions(
                                        listPermissionsNeeded,  // new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        Constant.PERMISSIONS_REQUEST_READ_WRITE_STORAGE
                                );
                            }
                        })
                        .show();
            } else {

                // No explanation needed, we can request the permission.
                fragment.requestPermissions(
                        listPermissionsNeeded,  // new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constant.PERMISSIONS_REQUEST_READ_WRITE_STORAGE
                );

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

            // No explanation needed, we can request the permission.
            /*ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission_group.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_CAMERA);*/
        } else {
            // perform the action to open camera
            callback.permissionGranted(PermissionTypes.READ_WRITE_STORAGE);
        }
    }

    public void requestCameraAudioPermissions(final Context context, final Activity returningActivity, PermissionCallback callback, final Fragment fragment) {

        int recordAudio = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
        int camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int modifyAudioSetting = ContextCompat.checkSelfPermission(context, Manifest.permission.MODIFY_AUDIO_SETTINGS);

        //final List<String> listPermissionsNeeded = new ArrayList<>();
        final String[] listPermissionsNeeded = new String[]{android.Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.MODIFY_AUDIO_SETTINGS};

        /*if (recordAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (modifyAudioSetting != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        }*/

        if (recordAudio != PackageManager.PERMISSION_GRANTED || camera != PackageManager.PERMISSION_GRANTED || modifyAudioSetting != PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(returningActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(returningActivity,
                    Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(returningActivity,
                    Manifest.permission.MODIFY_AUDIO_SETTINGS)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.i("Permission", "Permission info required for CAMERA and STORAGE");
                new AlertDialog.Builder(context)
                        //.setTitle("Delete entry")
                        .setCancelable(false)
                        .setMessage("Please provide access to your camera and microphone for making video calls.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                // No explanation needed, we can request the permission.
                                fragment.requestPermissions(
                                        listPermissionsNeeded,
                                        Constant.PERMISSIONS_REQUEST_CAMERA_AUDIO);
                            }
                        })
                        .show();
            } else {
                fragment.requestPermissions(
                        listPermissionsNeeded,
                        Constant.PERMISSIONS_REQUEST_CAMERA_AUDIO);
            }

        } else {
            callback.permissionGranted(PermissionTypes.PERMISSIONS_REQUEST_CAMERA_AUDIO);
        }
    }

    public interface PermissionCallback {
        void permissionGranted(PermissionTypes permissionType);
        void permissionDenied(PermissionTypes permissionType);
    }
}
