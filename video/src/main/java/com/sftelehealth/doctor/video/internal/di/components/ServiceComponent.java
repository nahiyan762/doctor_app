package com.sftelehealth.doctor.video.internal.di.components;

import com.sftelehealth.doctor.video.internal.di.PerAndroidComponent;
import com.sftelehealth.doctor.video.internal.di.modules.ServiceModule;
import com.sftelehealth.doctor.video.service.AgoraHelperService;

import dagger.Component;

/**
 * Created
 */
@PerAndroidComponent
@Component(dependencies = CoreVideoComponent.class, modules = {ServiceModule.class})
public interface ServiceComponent {
    void inject(AgoraHelperService agoraHelperService);
}
