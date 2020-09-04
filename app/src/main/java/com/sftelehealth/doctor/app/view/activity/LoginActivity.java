package com.sftelehealth.doctor.app.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.internal.di.components.DaggerUseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.components.UseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.modules.UseCaseModule;
import com.sftelehealth.doctor.app.view.fragment.LoginFragment;
import com.sftelehealth.doctor.app.view.utils.ActivityUtils;
import com.sftelehealth.doctor.app.view.viewmodel.LoginFragmentViewModel;
import com.sftelehealth.doctor.app.view.viewmodel.ViewModelFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import timber.log.Timber;

public class LoginActivity extends BaseAppCompatActivity {

    //@Inject public SharedPreferences sp;

    // @Inject
    // public LoginFragmentViewModel loginFragmentViewModel;

    LoginFragment loginFragment;

    //NetworkComponent networkComponent;
    UseCaseComponent useCaseComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Use this to get an updated certificate/library for openSSL from Google play
        // in case there is any security flaw which was found and fixed by Google
        try {
            ProviderInstaller.installIfNeeded(this);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
            Timber.w("Google Play Services Exception");
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            Timber.w("Google Play Services not installed");
        }

        initializeInjector();
        // this.getComponent().inject(this);

        loginFragment = findOrCreateViewFragment();

        this.initializeActivity(savedInstanceState);

        //LoginFragment tasksFragment = findOrCreateViewFragment();

        // loginFragmentViewModel = findOrCreateViewModel();
        // findOrCreateViewModel();

       // loginFragmentViewModel = ViewModelProviders.of(loginFragment).get(LoginFragmentViewModel.class);

        // Link View and ViewModel
        // loginFragment.setViewModel(loginFragmentViewModel);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    private void setupToolbar() {
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //AccessibilityEvent.
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            // if there is no data in savedInstanceState then get data from intent to set initial required values
            //this.userId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_USER_ID, -1);
        } else {
            // if there is data in savedInstanceState then use it to setup the required initial values for
            //this.userId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_USER_ID);
        }
    }

    @NonNull
    private LoginFragment findOrCreateViewFragment() {

        // Check if the fragment exists with for the existing view id,
        // if does then find an instance for it or else create a new one and add to the view i
        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (loginFragment == null) {
            // Create the fragment
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    loginFragment,
                    R.id.content_frame
            );
        }
        return loginFragment;
    }

    public LoginFragmentViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = new ViewModelFactory(useCaseComponent);

        return ViewModelProviders.of(activity, factory).get(LoginFragmentViewModel.class);
    }

    private void initializeInjector() {
        this.useCaseComponent = DaggerUseCaseComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .useCaseModule(new UseCaseModule())
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
