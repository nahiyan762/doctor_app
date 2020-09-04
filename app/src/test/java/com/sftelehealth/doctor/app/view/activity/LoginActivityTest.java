package com.sftelehealth.doctor.app.view.activity;

import android.app.Activity;

import com.sftelehealth.doctor.BuildConfig;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    Activity activity;

    @Before
    public void setUp() {

        activity = Robolectric.setupActivity(LoginActivity.class);

        assertNotNull(activity);

        /*button = (Button) activity.findViewById(R.id.);
        emailView = (EditText) activity.findViewById(R.id.email);
        passwordView = (EditText) activity.findViewById(R.id.password);*/
    }
}
