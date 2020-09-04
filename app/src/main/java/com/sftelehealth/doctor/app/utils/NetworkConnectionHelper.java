package com.sftelehealth.doctor.app.utils;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.sftelehealth.doctor.app.receiver.NetworkChangeReceiver;

/**
 * Created by Rahul on 21/06/17.
 */

public class NetworkConnectionHelper {

    public static void registerNetworkChangeReceiver(Context context, NetworkChangeReceiver networkChangeReceiver) {
        context.registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public static void unRegisterNetworkChangeReceiver(Context context, NetworkChangeReceiver networkChangeReceiver) {
        context.unregisterReceiver(networkChangeReceiver);
    }
}