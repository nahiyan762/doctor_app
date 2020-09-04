package com.sftelehealth.doctor.app.internal.di.components;

import dagger.Component;
import com.sftelehealth.doctor.app.internal.di.PerActivity;
import com.sftelehealth.doctor.app.internal.di.modules.ActivityModule;
import com.sftelehealth.doctor.app.internal.di.modules.UseCaseModule;
import com.sftelehealth.doctor.app.view.viewmodel.ViewModelFactory;

/**
 * Created by Rahul on 04/10/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UseCaseModule.class})
public interface UseCaseComponent extends ActivityComponent {
    //void inject(LoginFragment loginFragment);
    // void inject(LoginActivity loginActivity);
    void inject(ViewModelFactory viewModelFactory);
}