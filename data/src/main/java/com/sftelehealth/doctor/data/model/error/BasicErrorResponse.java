package com.sftelehealth.doctor.data.model.error;

/**
 * Created by Rahul on 09/02/17.
 */

public class BasicErrorResponse {

    String error;
    String errorSend;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorSend() {
        return errorSend;
    }

    public void setErrorSend(String errorSend) {
        this.errorSend = errorSend;
    }
}
