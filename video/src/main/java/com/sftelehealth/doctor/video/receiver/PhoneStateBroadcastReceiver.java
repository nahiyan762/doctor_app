package com.sftelehealth.doctor.video.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class PhoneStateBroadcastReceiver extends BroadcastReceiver {

    CustomPhoneStateListener customPhoneStateListener;

    public PhoneStateBroadcastReceiver(CustomPhoneStateListener customPhoneStateListener) {
        this.customPhoneStateListener = customPhoneStateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(customPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
