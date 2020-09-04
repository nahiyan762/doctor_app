package com.sftelehealth.doctor.app.internal.di.components;

import android.app.Activity;

import dagger.Component;
import com.sftelehealth.doctor.app.internal.di.PerActivity;
import com.sftelehealth.doctor.app.internal.di.modules.ActivityModule;

/**
 * Created by Rahul on 26/06/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
interface ActivityComponent {
    //Exposed to sub-graphs.
    Activity activity();
}
