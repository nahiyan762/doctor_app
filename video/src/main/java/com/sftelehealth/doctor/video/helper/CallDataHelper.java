package com.sftelehealth.doctor.video.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.sftelehealth.doctor.video.agora.call.CallData;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class CallDataHelper {

    static Gson gson = new Gson();

    public synchronized static CallData getCallData(SharedPreferences sp) {

        return gson.fromJson(sp.getString("call_data", "{state:{videoCallState:-1}}"), CallData.class);
    }

    public synchronized static void saveCallData(CallData callData, SharedPreferences sp) {
        sp.edit().putString("call_data", gson.toJson(callData, CallData.class)).commit();
    }

    public synchronized static void notifyAgoraCallStatus(Context context) {
        Intent intent = new Intent("refresh_video_call_status");

        // You can also include some extra data.
        intent.putExtra("action", "refresh");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}