package com.sftelehealth.doctor.app.utils.onesignal;

import android.content.Context;
import android.content.SharedPreferences;

import com.onesignal.OneSignal;

import com.sftelehealth.doctor.app.Doctor24x7Application;
import com.sftelehealth.doctor.app.utils.Constant;
import timber.log.Timber;

/**
 * Dependency injections are not use in such classes
 * {http://stackoverflow.com/questions/39844814/android-dagger-2-injecting-application-context-to-a-non-activity-class}
 *
 */

public class IdsHandler implements OneSignal.IdsAvailableHandler {

    Context application;

    //@Inject
    public SharedPreferences sp;

    public IdsHandler(Doctor24x7Application application) {

        this.application = application;
        //application.getApplicationComponent().inject(this);

        sp = application.getSharedPreferences(Constant.USER_PREFS, Context.MODE_PRIVATE);
    }

    @Override
    public void idsAvailable(String userId, String registrationId) {
        Timber.d("User: %s", userId);

        if (registrationId != null)
            Timber.d("registrationId: %s", registrationId);

        // save one signal user id and gcm registration id
        sp.edit().putString(Constant.ONESIGNAL_ID, userId).apply();
        sp.edit().putString(Constant.GCM_REGISTRATION_ID, registrationId).apply();

        //updateRegistrationId(userId);
    }
}
