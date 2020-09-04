package com.sftelehealth.doctor.data.model.response;

/**
 * Created by rahul on 02/10/16.
 */
public class SendOtpResponse extends BasicResponse {

    boolean success, isRegistered;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}
