package com.sftelehealth.doctor.app.events;

/**
 * Created by Rahul on 22/06/17.
 */

public class NetworkChangeEvent {

    boolean networkAvailable;

    public boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        this.networkAvailable = networkAvailable;
    }
}
