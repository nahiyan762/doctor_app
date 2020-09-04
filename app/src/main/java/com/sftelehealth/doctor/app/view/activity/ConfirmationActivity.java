package com.sftelehealth.doctor.app.view.activity;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import android.widget.Button;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.internal.di.components.DaggerUseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.components.UseCaseComponent;
import com.sftelehealth.doctor.app.internal.di.modules.UseCaseModule;
import com.sftelehealth.doctor.app.view.fragment.ConfirmationFragment;
import com.sftelehealth.doctor.app.view.utils.ActivityUtils;
import com.sftelehealth.doctor.app.view.viewmodel.ConfirmationFragmentViewModel;
import com.sftelehealth.doctor.app.view.viewmodel.ViewModelFactory;

public class ConfirmationActivity extends BaseAppCompatActivity {

    Button confirmOtp;

    UseCaseComponent useCaseComponent;

    ConfirmationFragment confirmationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        initializeInjector();

        confirmationFragment = findOrCreateViewFragment();
    }

    @NonNull
    private ConfirmationFragment findOrCreateViewFragment() {

        // Check if the fragment exists with for the existing view id,
        // if does then find an instance for it or else create a new one and add to the view i
        ConfirmationFragment confirmationFragment =
                (ConfirmationFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (confirmationFragment == null) {
            // Create the fragment
            confirmationFragment = ConfirmationFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    confirmationFragment,
                    R.id.content_frame
            );
        }
        return confirmationFragment;
    }

    public ConfirmationFragmentViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = new ViewModelFactory(useCaseComponent);

        return ViewModelProviders.of(activity, factory).get(ConfirmationFragmentViewModel.class);
    }

    private void initializeInjector() {
        this.useCaseComponent = DaggerUseCaseComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .useCaseModule(new UseCaseModule())
                .build();
    }
}
