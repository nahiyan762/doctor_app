package com.sftelehealth.doctor.video.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public static final int NETWORK_AVAILABLE = 0;
    public static final int NETWORK_UNAVAILABLE = 1;

    ConnectivityChangeListener connectivityChangeListener;

    public NetworkChangeReceiver(ConnectivityChangeListener connectivityChangeListener) {
        this.connectivityChangeListener = connectivityChangeListener;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkUtil.getConnectivityStatusString(context);
        Log.e("NetworkChangeReceiver", "Network change detected");
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED && connectivityChangeListener!= null) {
                connectivityChangeListener.onConnectivityChanged(NETWORK_UNAVAILABLE);
            } else {
                connectivityChangeListener.onConnectivityChanged(NETWORK_AVAILABLE);
            }
        }
    }

    public interface ConnectivityChangeListener {
        void onConnectivityChanged(int connectivityStatus);
    }
}