package com.sftelehealth.doctor.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import javax.inject.Inject;

import com.sftelehealth.doctor.app.events.NetworkChangeEvent;
import com.sftelehealth.doctor.app.helpers.ConnectivityStatus;
import com.sftelehealth.doctor.app.utils.EventBus;

/**
 * Created by Rahul on 21/06/17.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Inject
    EventBus eventBus;

    @Override
    public void onReceive(Context context, Intent intent) {

        eventBus = EventBus.instanceOf();

        NetworkChangeEvent networkChangeEvent = new NetworkChangeEvent();
        Log.d("app","Network connectivity change");
        if(!ConnectivityStatus.isConnected(context)){
            // no connection
            /**
             * if there is no connection then send an event using event bus
             * with the state of the network
             */
            networkChangeEvent.setNetworkAvailable(false);
            eventBus.send(networkChangeEvent);
        }else {
            // connected
            /**
             * send an event on the event bus stating the change in the network
             * so that in case if any request has failed then it can be refreshed
             */
            networkChangeEvent.setNetworkAvailable(true);
            eventBus.send(networkChangeEvent);
        }

    }
}