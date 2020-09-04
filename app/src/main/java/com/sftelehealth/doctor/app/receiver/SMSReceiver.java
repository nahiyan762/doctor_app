package com.sftelehealth.doctor.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSReceiver extends BroadcastReceiver {

    OTPReceiver otpReceiver;

    public SMSReceiver(OTPReceiver otpReceiver) {
        this.otpReceiver = otpReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
            switch (status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    // Get SMS message contents
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    // Extract one-time code from the message and complete verification
                    // by sending the code back to your server for SMS authenticity.
                    Log.i("SMSReceiver", "message - " + message);
                    /*String[] messageSplit = message.split(" ");
                    otpReceiver.otpReceived(messageSplit[3]);*/

                    Pattern p = Pattern.compile("\\d{6}");
                    Matcher m = p.matcher(message);

                    if(m.find())
                        otpReceiver.otpReceived(m.group(0));

                    break;
                case CommonStatusCodes.TIMEOUT:
                    // Waiting for SMS timed out (5 minutes)
                    // Handle the error ...
                    Log.i("SMSReceiver", "timeout");
                    break;
            }
        }
    }

    public interface OTPReceiver {
        void otpReceived(String otp);
    }
}
