package com.sftelehealth.doctor.app.view.activity;

import androidx.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.Menu;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.internal.di.components.DaggerUseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.components.UseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.modules.UseCaseModule;
import com.sftelehealth.doctor.app.view.fragment.CreatePrescriptionActivityFragment;
import com.sftelehealth.doctor.app.view.fragment.PreviewPrescriptionFragment;
import com.sftelehealth.doctor.app.view.utils.ActivityUtils;
import com.sftelehealth.doctor.app.view.viewmodel.PrescriptionActivityFragmentViewModel;
import com.sftelehealth.doctor.app.view.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import static com.sftelehealth.doctor.video.Constant.REFRESH_VIDEO_CALL_STATUS;

public class PrescriptionActivity extends BaseAppCompatActivity implements ActivityUtils.AgoraCallStatusChangeListener {

    CreatePrescriptionActivityFragment createPrescriptionActivityFragment;
    PreviewPrescriptionFragment previewPrescriptionFragment;

    PrescriptionActivityFragmentViewModel viewModel;

    BroadcastReceiver agoraCallStatusListener;

    Menu menu;

    @Inject
    UseCaseComponent useCaseComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        initializeInjector();

        if(getIntent().getStringExtra("prescription") != null) {
            showPreview(true);
        } else {
            createPrescriptionActivityFragment = findOrCreateViewFragment();
        }

        ActivityUtils.showHideStatusBarCallStatusBar(this);
        agoraCallStatusListener = ActivityUtils.getVideoCallStatusReceiver(this);
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
    private CreatePrescriptionActivityFragment findOrCreateViewFragment() {

        // Check if the fragment exists with for the existing view id,
        // if does then find an instance for it or else create a new one and add to the view i
        CreatePrescriptionActivityFragment createPrescriptionActivityFragment =
                (CreatePrescriptionActivityFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (createPrescriptionActivityFragment == null) {
            // Create the fragment
            createPrescriptionActivityFragment = CreatePrescriptionActivityFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    createPrescriptionActivityFragment,
                    R.id.content_frame
            );
        }
        return createPrescriptionActivityFragment;
    }

    public PrescriptionActivityFragmentViewModel obtainViewModel(FragmentActivity activity) {

        if(viewModel == null) {
            // Use a Factory to inject dependencies into the ViewModel
            ViewModelFactory factory = new ViewModelFactory(useCaseComponent);
            viewModel = ViewModelProviders.of(activity, factory).get(PrescriptionActivityFragmentViewModel.class);
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

    public void showPreview(boolean prescriptionPassedOnIntent) {
        if(prescriptionPassedOnIntent) {
            previewPrescriptionFragment = PreviewPrescriptionFragment.newInstance(getIntent().getStringExtra("prescription"));
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    previewPrescriptionFragment,
                    R.id.content_frame
            );
        } else {
            previewPrescriptionFragment = PreviewPrescriptionFragment.newInstance(null);
            ActivityUtils.addFragmentToActivityAndBackstack(
                    getSupportFragmentManager(),
                    previewPrescriptionFragment,
                    R.id.content_frame
            );
        }
    }

    public void editPrescription() {
        createPrescriptionActivityFragment = CreatePrescriptionActivityFragment.newInstance();
        ActivityUtils.addFragmentToActivityAndBackstack(
                getSupportFragmentManager(),
                createPrescriptionActivityFragment,
                R.id.content_frame
        );
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu, inflater);
        //if(getIntent().getStringExtra("prescription") != null) {
        getMenuInflater().inflate(R.menu.pres_prev_edit, menu);
        this.menu = menu;
        this.menu.findItem(R.id.edit_prescription).setVisible(viewModel.isPrescriptionEditable.get());

        return true;
    }

    @Override
    public void onStatusChange(int status) {
        // If status changed then change toolbar
        ActivityUtils.showHideStatusBarCallStatusBar(this);
    }
}
