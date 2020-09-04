package com.sftelehealth.doctor.doctor24x7_doctor;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.junit.After;
import org.junit.Before;
import org.robolectric.shadows.gms.Shadows;
import org.robolectric.shadows.gms.common.ShadowGoogleApiAvailability;

public class TestBase {

    @Before
    public void setUp() throws Exception {
        final ShadowGoogleApiAvailability shadowGoogleApiAvailability
                = Shadows.shadowOf(GoogleApiAvailability.getInstance());
        shadowGoogleApiAvailability.setIsGooglePlayServicesAvailable(ConnectionResult.SUCCESS);
    }

    @After
    public void tearDown() throws Exception {
    }
}
