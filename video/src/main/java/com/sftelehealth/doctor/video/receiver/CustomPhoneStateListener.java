package com.sftelehealth.doctor.video.receiver;

import android.content.Context;
import android.telephony.PhoneStateListener;

public class CustomPhoneStateListener extends PhoneStateListener {

    PhoneStateChangedListener phoneStateChangedListener;
    private final String TAG = "PhoneStateListener";

    //private static final String TAG = "PhoneStateChanged";
    Context context; //Context to make Toast if required
    public CustomPhoneStateListener(Context context, PhoneStateChangedListener phoneStateChangedListener) {
        super();
        this.phoneStateChangedListener = phoneStateChangedListener;
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        phoneStateChangedListener.phoneStateChanged(state);
    }

    public interface PhoneStateChangedListener {
        void phoneStateChanged(int phoneState);
    }
}
