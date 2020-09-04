package com.sftelehealth.doctor.data.model;

public class Timers {

    private int callbackRejectSec;
    private int callbackExpireSec;
    private int videoCallbackRejectSec;

    public int getCallbackRejectSec() {
        return callbackRejectSec;
    }

    public int getCallbackExpireSec() {
        return callbackExpireSec;
    }

    public int getVideoCallbackRejectSec() {
        return videoCallbackRejectSec;
    }
}
