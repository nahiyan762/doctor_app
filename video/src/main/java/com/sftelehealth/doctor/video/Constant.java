package com.sftelehealth.doctor.video;

public class Constant {

    // Video Call
    public static final String SIGNALLING_SDK_STATE = "signalling_sdk_state";
    public static final String VIDEO_CALL_STATE = "video_call_state";

    // SharedPreference
    public static final String USER_PREFS = BuildConfig.PACKAGE_NAME.replace(".video","")+".user_prefs";

    // Static values
    public static final String ISD_CODE = "isd_code";
    public static final String COUNTRY_CODE = "country_code";

    // Video Call Status
    public static final int VIDEO_CALL_STATE_INITIATED = 0;
    public static final int VIDEO_CALL_STATE_INVITE_RECEIVED_BY_USER = 1;
    public static final int VIDEO_CALL_STATE_ON_GOING = 2;
    public static final int VIDEO_CALL_STATE_PAUSED = 3;
    public static final int VIDEO_CALL_STATE_ENDED = -1;

    // Agora Signalling API Event
    public static final int AGORA_API_LOGIN_FAILED = 0;
    public static final int AGORA_API_LOGIN_SUCCESS = 1;
    public static final int AGORA_RTC_INSTANCE_ACQUIRED = 2;
    public static final int AGORA_FIRST_LOCAL_VIDEO_FRAME = 3;

    // Video Call Event
    public static final int VIDEO_CALL_EVENT_INVITE_DELIVERED = 0;
    public static final int VIDEO_CALL_EVENT_INVITE_ACCEPTED = 1;
    public static final int VIDEO_CALL_EVENT_INVITE_REFUSED = 2;
    public static final int VIDEO_CALL_EVENT_INVITE_FAILED = 3;
    public static final int VIDEO_CALL_EVENT_INVITE_ENDED_BY_PEER = 4;
    public static final int VIDEO_CALL_EVENT_INVITE_ENDED_BY_MYSELF = 5;

    // Notification ID for the notification which will show the status of the call until call ends
    public static final int VIDEO_CALL_NOTIFICATION_ID = 101;


    // Doctor ID String
    public static final String STR_DOC_ID = "string_doctor_id";

    // Communication
    public static final String VIDEO_MESSAGE_ON_ANOTHER_CALL = "on_another_call";
    public static final String VIDEO_MESSAGE_SEND_USER_INFO = "send_user_info";

    // BroadcastReceiver
    public static final String REFRESH_VIDEO_CALL_STATUS = "refresh_video_call_status";
}
