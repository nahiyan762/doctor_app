package com.sftelehealth.doctor.app.utils.onesignal;

import android.content.Context;
import android.content.Intent;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import org.json.JSONObject;
import com.sftelehealth.doctor.app.view.activity.CallbackDetailsActivity;
import com.sftelehealth.doctor.app.view.activity.CaseDetailsActivity;
import com.sftelehealth.doctor.app.view.activity.MainActivity;
import timber.log.Timber;
import static com.sftelehealth.doctor.app.utils.Constant.CALLBACK;
import static com.sftelehealth.doctor.app.utils.Constant.CALLBACK_ID;
import static com.sftelehealth.doctor.app.utils.Constant.CALLBACK_LIST;
import static com.sftelehealth.doctor.app.utils.Constant.CASE_ID;
import static com.sftelehealth.doctor.app.utils.Constant.DOCUMENT_ID;
import static com.sftelehealth.doctor.app.utils.Constant.HOME_CALLBACK;
import static com.sftelehealth.doctor.app.utils.Constant.MEDICAL_CASE;

/**
 * Created by Rahul on 24/01/17.
 */

public class NotificationOpenAction implements OneSignal.NotificationOpenedHandler{

    Context context;

    public NotificationOpenAction (Context context) {
        this.context = context;
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        OSNotificationAction.ActionType actionType = result.action.type;
        JSONObject data = result.notification.payload.additionalData;

        String customAction = "default";

        if(data != null) {
            customAction = data.optString("action");
        }

        if (actionType == OSNotificationAction.ActionType.ActionTaken)
            Timber.i("Button pressed with id: %s", result.action.actionID);

        // The following can be used to open an Activity of your choice.
        // Replace - getApplicationContext() - with any Android Context.
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        switch (customAction) {
            case CALLBACK_LIST :
                intent.setClass(context, MainActivity.class);
                intent.putExtra(CALLBACK_ID, data.optInt("callbackId"));
                context.startActivity(intent);
                break;
            case CALLBACK :
                intent.setClass(context, CallbackDetailsActivity.class);
                intent.putExtra(CALLBACK_ID, data.optInt("callbackId"));
                if(data.has("documentId")) {
                    intent.putExtra(DOCUMENT_ID, "documentId");
                }
                context.startActivity(intent);
                break;
            case MEDICAL_CASE :
                intent.setClass(context, CaseDetailsActivity.class);
                //Navigator.navigateToCaseDetails();
                intent.putExtra(CASE_ID, data.optInt("medicalCaseId"));
                context.startActivity(intent);
                break;
            case HOME_CALLBACK :
                intent.setClass(context, MainActivity.class);
                intent.putExtra(CALLBACK_ID, data.optInt("callbackId"));
                context.startActivity(intent);   //not coming
                break;
            default:
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(context, MainActivity.class);
                context.startActivity(intent);
        }


        // Add the following to your AndroidManifest.xml to prevent the launching of your main Activity
        //   if you are calling startActivity above.
     /*
        <application ...>
          <meta-data android:name="com.onesignal.NotificationOpened.DEFAULT" android:value="DISABLE" />
        </application>
     */

    }
}
