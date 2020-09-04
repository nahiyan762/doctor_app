package com.sftelehealth.doctor.app.utils.onesignal;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;
import com.sftelehealth.doctor.app.Doctor24x7Application;
import com.sftelehealth.doctor.app.services.NotificationTextToSpeechService;
import com.sftelehealth.doctor.app.services.UpdateContactIntentService;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import timber.log.Timber;

/**
 * Created by Rahul on 24/01/17.
 */

public class NotificationHandler implements OneSignal.NotificationReceivedHandler {

    //Realm realm;
    Gson gson;
    boolean insertToDb = false;
    Context context;

    // @Inject public SharedPreferences sp;

    Doctor24x7Application application;

    public NotificationHandler(Doctor24x7Application application) {
        this.application = application;
        this.context = application;
        //Realm.init(context);
    }

    @Override
    public void notificationReceived(OSNotification notification) {
        //application.getApplicationComponent().inject(this);

        JSONObject data = notification.payload.additionalData;
        Log.d("OneSignalNotification", "data - " + data.toString());

        if (data.has("action")) {
            String action = "";
            try {
                action = data.get("action").toString();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            switch (action) {
                case "callbackList":
                    //sendRefreshBroadcast();
                    break;
                case "callback": NotificationTextToSpeechService.startTextToSpeech(context, notification.payload.body, notification.payload.additionalData);
                    sendRefreshBroadcast();
                    break;
                case "medicalCase":
                    sendRefreshBroadcast();
                    break;
                case "homeCallback": break;
                case "update_contact":
                    Intent intent = new Intent();
                    intent = new Intent(context, UpdateContactIntentService.class);
                    //intent.setClassName(context, UpdateContactIntentService.class.toString());
                    intent.putExtra("phone", data.optString("phone"));
                    context.startService(intent);
                    break;
                case "uninstall_check": break;
            }
        }
    }

    private void sendCallStatusBroadcast() {
        //Intent intent=new Intent();
        //intent.setAction(context.getPackageName() + ".REFRESH");
        //intent.putExtra("action", "refresh");
        //context.sendBroadcast(intent);

        Timber.d("Broadcasting message");
        Intent intent = new Intent("refresh_call_status");    // "custom-event-name"
        // You can also include some extra data.
        intent.putExtra("action", "refresh");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void sendRefreshBroadcast() {
        //Intent intent=new Intent();
        //intent.setAction(context.getPackageName() + ".REFRESH");
        //intent.putExtra("action", "refresh");
        //context.sendBroadcast(intent);

        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("refresh_event");    // "custom-event-name"
        // You can also include some extra data.
        intent.putExtra("action", "refresh");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}