package com.sftelehealth.doctor.video.internal.di.components;

import android.content.ContextWrapper;

import com.sftelehealth.doctor.video.internal.di.PerAndroidComponent;
import com.sftelehealth.doctor.video.internal.di.modules.AndroidModule;

import dagger.Component;

/**
 *
 */
@PerAndroidComponent
@Component(dependencies = CoreVideoComponent.class, modules = AndroidModule.class)
interface AndroidComponent {
    // Exposed to sub-graphs.
    ContextWrapper contextWrapper();
}
