package com.sftelehealth.doctor.app.utils;

import com.sftelehealth.doctor.BuildConfig;

/**
 * Created by Rahul on 23/06/17.
 */

public class Constant {

    public static final String USER_PREFS = BuildConfig.APPLICATION_ID+".user_prefs";
    public static final String PHONE = "phone";
    public static final String TRANSITION_PHONE_DATA = "phoneNumber";

    public static final String OTP = "otp";
    public static final String CASE_ID = "case_id";
    public static final String CONSULT_ID = "consult_id";
    public static final String CASE = "case";
    public static final String CALLBACK_ID = "callback_id";
    public static final String DOCUMENT_ID = "document_id";
    public static final String DOCTOR_ID = "doctor_id";
    public static final String DOCTOR_CATEGORY_ID = "doctor_category_id";
    public static final String PRESCRIPTION = "prescription";
    public static final String APP_VERSION = "appVersion";
    public static final String ONESIGNAL_ID = "onesignal_id";
    public static final String GCM_REGISTRATION_ID = "gcm_registration_id";

    public static final String CALLBACK_LIST = "callbackList";
    public static final String CALLBACK = "callback";
    public static final String MEDICAL_CASE = "medicalCase";
    public static final String HOME_CALLBACK = "homeCallback";

    public static final String DOCTOR_CONTACT_NAME = BuildConfig.APP_NAME;

    // Permission codes
    public static final int PERMISSIONS_REQUEST_GET_ACCOUNTS = 2;
    public static final int PERMISSIONS_REQUEST_CAMERA = 3;
    public static final int PERMISSIONS_REQUEST_CONTACTS = 4;
    public static final int PERMISSIONS_REQUEST_READ_WRITE_STORAGE = 5;
    public static final int PERMISSIONS_REQUEST_CAMERA_AUDIO = 6;

    // Intent identifier constants
    public static final int REQUEST_CAMERA = 101;
    public static final int SELECT_IMAGE = 102;
    // public static final int SELECT_FILE = 102;
    public static final int SELECT_DOCUMENT = 103;
    public static final int SHARE_FILE = 104;

    // App Update
    public static final int APP_UPDATE_IMMEDIATE = 140;

    // Video Call
    public static final String SIGNALLING_SDK_STATE = "signalling_sdk_state";
    public static final String VIDEO_CALL_STATE = "video_call_state";

    // Video Call Status
    public static final int VIDEO_CALL_INITIATE = 0;
    public static final int VIDEO_CALL_ON_GOING = 1;
    public static final int VIDEO_CALL_ENDED = 2;

    // Notification ID for the notification which will show the status of the call until call ends
    public static final int VIDEO_CALL_NOTIFICATION_ID = 101;
}
