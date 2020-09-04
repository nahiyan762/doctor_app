package com.sftelehealth.doctor.app.listener;

/**
 * Created by Rahul on 11/01/18.
 */

public interface CallbackDetailsEventListener {
    void acceptOrRejectCallback(boolean isAccepted);
    void initiateCall();
    void initiateEmergencyCall();
    void addViewPrescription();
    void showPriorCaseDetailsView();
}
