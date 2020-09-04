package com.sftelehealth.doctor.app.view.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.internal.di.components.DaggerUseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.components.UseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.modules.UseCaseModule;
import com.sftelehealth.doctor.app.view.fragment.CallbackDetailsActivityFragment;
import com.sftelehealth.doctor.app.view.fragment.DocumentViewFragment;
import com.sftelehealth.doctor.app.view.fragment.PriorCaseDetailsFragment;
import com.sftelehealth.doctor.app.view.utils.ActivityUtils;
import com.sftelehealth.doctor.app.view.viewmodel.CallbackDetailsActivityFragmentViewModel;
import com.sftelehealth.doctor.app.view.viewmodel.ViewModelFactory;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.sftelehealth.doctor.video.Constant.REFRESH_VIDEO_CALL_STATUS;

public class CallbackDetailsActivity extends BaseAppCompatActivity implements ActivityUtils.AgoraCallStatusChangeListener {

    UseCaseComponent useCaseComponent;

    CallbackDetailsActivityFragment callbackDetailsActivityFragment;
    PriorCaseDetailsFragment priorCaseDetailsFragment;

    BroadcastReceiver agoraCallStatusListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback_details);

        initializeInjector();

        ActivityUtils.setUpToolbar(this, "Appointment Request");
        ActivityUtils.showHideStatusBarCallStatusBar(this);
        agoraCallStatusListener = ActivityUtils.getVideoCallStatusReceiver(this);

        findOrCreateViewFragment();
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

    @NonNull
    private void findOrCreateViewFragment() {

        // Check if the fragment exists with for the existing view id,
        // if does then find an instance for it or else create a new one and add to the view
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        /*if(currentFragment instanceof PriorCaseDetailsFragment) {
            priorCaseDetailsFragment = (PriorCaseDetailsFragment) currentFragment;
        } else {
            callbackDetailsActivityFragment = (CallbackDetailsActivityFragment) currentFragment;
        }*/
        /*CallbackDetailsActivityFragment callbackDetailsActivityFragment =
                (CallbackDetailsActivityFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);*/
        if (currentFragment == null) {
            // Create the fragment
            callbackDetailsActivityFragment = CallbackDetailsActivityFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    callbackDetailsActivityFragment,
                    R.id.content_frame
            );
        }
    }

    public CallbackDetailsActivityFragmentViewModel obtainViewModel(FragmentActivity activity) {

        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = new ViewModelFactory(useCaseComponent);
        return ViewModelProviders.of(activity, factory).get(CallbackDetailsActivityFragmentViewModel.class);
    }

    private void initializeInjector() {
        this.useCaseComponent = DaggerUseCaseComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .useCaseModule(new UseCaseModule())
                .build();
    }

    public void showPriorCaseDetailsFragment() {
        priorCaseDetailsFragment = PriorCaseDetailsFragment.newInstance();
        ActivityUtils.addFragmentToActivityAndBackstack(
                getSupportFragmentManager(),
                priorCaseDetailsFragment,
                R.id.content_frame
        );
    }

    public void showDocumentPreview(String documentId) {

        DocumentViewFragment documentViewFragment = DocumentViewFragment.newInstance(documentId);
        ActivityUtils.addFragmentToActivityAndBackstack(
                getSupportFragmentManager(),
                documentViewFragment,
                R.id.content_frame
        );
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else if (isTaskRoot()) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(getApplicationContext(), MainActivity.class);
            finish();
            startActivity(intent);
            // then go to parent activity

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStatusChange(int status) {
        // If status changed then change toolbar
        ActivityUtils.showHideStatusBarCallStatusBar(this);
    }
}
