package com.sftelehealth.doctor.video.internal.di.components;

import android.app.Activity;

import com.sftelehealth.doctor.video.internal.di.PerActivity;
import com.sftelehealth.doctor.video.internal.di.modules.ActivityModule;

import dagger.Component;

/**
 * Created by Rahul on 26/06/17.
 */
@PerActivity
@Component(dependencies = CoreVideoComponent.class, modules = ActivityModule.class)
interface ActivityComponent {
    // Exposed to sub-graphs.
    Activity activity();
}