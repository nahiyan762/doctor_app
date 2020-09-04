package com.sftelehealth.doctor.data;

import android.app.Application;

/**
 * Created by Rahul on 04/11/17.
 */

public class DataModule extends Application {


    /** Instance of the current application. */
    private static DataModule instance;


    public static synchronized DataModule getInstance() {
        if(instance == null) {
            instance = new DataModule();
        }

        return instance;
    }

}
