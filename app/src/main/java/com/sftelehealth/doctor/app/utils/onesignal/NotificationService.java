package com.sftelehealth.doctor.app.utils.onesignal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;

import com.sftelehealth.doctor.R;


/**
 * Created by Rahul on 29/03/17.
 * implementation from http://androidbash.com/android-push-notification-service-using-onesignal/
 */

public class NotificationService extends NotificationExtenderService {

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {

        OverrideSettings overrideSettings = new OverrideSettings();
        overrideSettings.extender = new NotificationCompat.Extender() {

            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                // Sets the background notification color to Red on Android 5.0+ devices.
                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.ic_stat_onesignal_default);
                builder.setLargeIcon(icon);
                builder.setSmallIcon(R.drawable.ic_onesignal_small_icon_default);
                return builder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));  // new BigInteger("FF0000FF", 16).intValue()
            }
        };

        //OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
        //Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);*/

        switch (receivedResult.payload.additionalData.optString("action", "")) {
            case "update_contact": return true;
            case "uninstall_check": return true;
            default: return false;
        }
    }
}