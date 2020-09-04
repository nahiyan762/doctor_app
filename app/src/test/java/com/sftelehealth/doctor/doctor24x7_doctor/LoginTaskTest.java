package com.sftelehealth.doctor.doctor24x7_doctor;

import android.widget.TextView;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.Doctor24x7Application;
import com.sftelehealth.doctor.app.view.activity.LoginActivity;
import com.sftelehealth.doctor.app.view.fragment.LoginFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Rahul on 05/09/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = Doctor24x7Application.class)
public class LoginTaskTest extends TestBase{
    // Login existing user into the app
    // @Rule public final ActivityTestRule

    @Test
    public void loginWithExistingUser() {
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        LoginFragment loginFragment = (LoginFragment) loginActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);

        //LoginFragmentViewModel viewModel = loginActivity.obtainViewModel(loginActivity);
        //viewModel.phoneNumber.set("9972808445");

        ((TextView)loginFragment.getView().findViewById(R.id.phone)).setText("9972808445");
        loginFragment.getView().findViewById(R.id.verify_btn).performClick();
    }
}
