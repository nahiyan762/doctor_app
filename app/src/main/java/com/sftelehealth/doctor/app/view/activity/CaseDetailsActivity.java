package com.sftelehealth.doctor.app.view.activity;

import androidx.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.internal.di.components.DaggerUseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.components.UseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.modules.UseCaseModule;
import com.sftelehealth.doctor.app.view.fragment.CaseDetailsActivityFragment;
import com.sftelehealth.doctor.app.view.fragment.DocumentViewFragment;
import com.sftelehealth.doctor.app.view.utils.ActivityUtils;
import com.sftelehealth.doctor.app.view.viewmodel.CaseDetailsActivityFragmentViewModel;
import com.sftelehealth.doctor.app.view.viewmodel.ViewModelFactory;

import static com.sftelehealth.doctor.video.Constant.REFRESH_VIDEO_CALL_STATUS;

public class CaseDetailsActivity extends BaseAppCompatActivity implements ActivityUtils.AgoraCallStatusChangeListener{

    CaseDetailsActivityFragment caseDetailsActivityFragment;
    DocumentViewFragment documentViewFragment;

    UseCaseComponent useCaseComponent;

    CaseDetailsActivityFragmentViewModel viewModel;

    BroadcastReceiver agoraCallStatusListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_details);

        initializeInjector();

        ActivityUtils.setUpToolbar(this, "Case Details");
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
        /*if(currentFragment instanceof DocumentViewFragment) {
            documentViewFragment = (DocumentViewFragment) currentFragment;
        } else {
            caseDetailsActivityFragment = (CaseDetailsActivityFragment) currentFragment;
        }*/
        /*CaseDetailsActivityFragment caseDetailsActivityFragment =
                (CaseDetailsActivityFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);*/
        if (currentFragment == null) {
            // Create the fragment
            caseDetailsActivityFragment = CaseDetailsActivityFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    caseDetailsActivityFragment,
                    R.id.content_frame
            );
        }
        //return caseDetailsActivityFragment;
    }

    public CaseDetailsActivityFragmentViewModel obtainViewModel(FragmentActivity activity) {
        if(viewModel == null) {
            // Use a Factory to inject dependencies into the ViewModel
            ViewModelFactory factory = new ViewModelFactory(useCaseComponent);
            viewModel = ViewModelProviders.of(activity, factory).get(CaseDetailsActivityFragmentViewModel.class);
        }
        return viewModel;
    }

    private void initializeInjector() {
        this.useCaseComponent = DaggerUseCaseComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .useCaseModule(new UseCaseModule())
                .build();
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
        } else if (isTaskRoot()) { //  getSupportFragmentManager().getBackStackEntryCount() == 0
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
