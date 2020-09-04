package com.sftelehealth.doctor.video.helper;

import android.app.ActivityManager;
import android.content.Context;

import com.sftelehealth.doctor.video.service.AgoraHelperService;

/**
 * This class is responsible for checking the status of the AgoraHelper service
 * If the service is running then it returns the status of the call which is ongoing
 * In case the service is not running it gives a status accordingly.
 *
 * It also emits broadcasts so that Activities can change their status in realtime if the call is cancelled or disconnected.
 *
 * Use Cases - Helps activities to show a call bar so that user can return back to the call once it has exited from the call screen
 *
 */
public class AgoraStatusHelper {

    Context context;

    public AgoraStatusHelper(Context context) { this.context = context; }

    public boolean isCallInProgress(Context context) {
        if(!isServiceRunning(context)) {
            // Check the status of the call and then return accordingly
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if the service is running
     * @return boolean
     */
    public static boolean isServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (AgoraHelperService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
