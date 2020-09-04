package com.sftelehealth.doctor.video.internal.di.components;

import android.content.Context;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.video.internal.di.modules.CoreVideoModule;
import com.sftelehealth.doctor.video.view.VideoConsultActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component which provides core objects for video module.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {CoreVideoModule.class})
public interface CoreVideoComponent {

    void inject(VideoConsultActivity videoConsultActivity);
    // void inject(AgoraHelperService agoraHelperService);

    //Exposed to sub-graphs.
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();

}