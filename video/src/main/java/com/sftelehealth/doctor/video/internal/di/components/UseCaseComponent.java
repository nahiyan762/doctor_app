package com.sftelehealth.doctor.video.internal.di.components;

import com.sftelehealth.doctor.video.internal.di.PerActivity;
import com.sftelehealth.doctor.video.internal.di.modules.ActivityModule;
import com.sftelehealth.doctor.video.internal.di.modules.UseCaseModule;
import com.sftelehealth.doctor.video.viewmodel.ViewModelFactory;

import dagger.Component;

/**
 * Created by Rahul on 04/10/17.
 */
@PerActivity
@Component(dependencies = CoreVideoComponent.class, modules = {ActivityModule.class, UseCaseModule.class})
public interface UseCaseComponent {
    //void inject(LoginFragment loginFragment);
    // void inject(LoginActivity loginActivity);
    void inject(ViewModelFactory viewModelFactory);
    //void inject(ContextWrapper contextWrapper);
}