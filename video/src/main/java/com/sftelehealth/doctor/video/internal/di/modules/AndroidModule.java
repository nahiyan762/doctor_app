package com.sftelehealth.doctor.video.internal.di.modules;

import android.content.ContextWrapper;

import com.sftelehealth.doctor.video.internal.di.PerAndroidComponent;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

    private final ContextWrapper contextWrapper;

    public AndroidModule(ContextWrapper contextWrapper) {
        this.contextWrapper = contextWrapper;
    }

    /**
     * Expose the component to dependents in the graph.
     */
    @Provides
    @PerAndroidComponent
    ContextWrapper contextWrapper() {
        return this.contextWrapper;
    }
}
