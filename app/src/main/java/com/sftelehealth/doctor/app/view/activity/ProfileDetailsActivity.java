package com.sftelehealth.doctor.app.view.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.internal.di.components.DaggerUseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.components.UseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.modules.UseCaseModule;
import com.sftelehealth.doctor.app.view.fragment.DoctorProfileFragment;
import com.sftelehealth.doctor.app.view.utils.ActivityUtils;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.sftelehealth.doctor.video.Constant.REFRESH_VIDEO_CALL_STATUS;

public class ProfileDetailsActivity extends BaseAppCompatActivity implements ActivityUtils.AgoraCallStatusChangeListener {

    UseCaseComponent useCaseComponent;
    DoctorProfileFragment doctorProfileFragment;

    BroadcastReceiver agoraCallStatusListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        ActivityUtils.setUpToolbarWithoutBackButton(this, "Profile Details");
        ActivityUtils.showHideStatusBarCallStatusBar(this);
        agoraCallStatusListener = ActivityUtils.getVideoCallStatusReceiver(this);

        initializeInjector();

        doctorProfileFragment = findOrCreateViewFragment();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ActivityUtils.showHideStatusBarCallStatusBar(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(agoraCallStatusListener, new IntentFilter(REFRESH_VIDEO_CALL_STATUS));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(agoraCallStatusListener);
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initializeInjector() {
        this.useCaseComponent = DaggerUseCaseComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .useCaseModule(new UseCaseModule())
                .build();
    }

    public UseCaseComponent getUseCaseComponent() {
        return useCaseComponent;
    }

    @NonNull
    private DoctorProfileFragment findOrCreateViewFragment() {

        // Check if the fragment exists with for the existing view id,
        // if does then find an instance for it or else create a new one and add to the view i
        DoctorProfileFragment doctorProfileFragment =
                (DoctorProfileFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (doctorProfileFragment == null) {
            // Create the fragment
            doctorProfileFragment = DoctorProfileFragment.newInstance(true);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    doctorProfileFragment,
                    R.id.content_frame
            );
        }

        return doctorProfileFragment;
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    @Override
    public void onStatusChange(int status) {
        // If status changed then change toolbar
        ActivityUtils.showHideStatusBarCallStatusBar(this);
    }
}
