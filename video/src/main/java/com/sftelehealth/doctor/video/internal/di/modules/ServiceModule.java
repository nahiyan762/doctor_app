package com.sftelehealth.doctor.video.internal.di.modules;

import com.sftelehealth.doctor.data.repository.VideoDataRepository;
import com.sftelehealth.doctor.data.repository.datastore.VideoDataStore;
import com.sftelehealth.doctor.data.repository.datastore.VideoDataStoreFactory;
import com.sftelehealth.doctor.domain.repository.VideoRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    VideoDataStore providesVideoDataStore(VideoDataStoreFactory videoDataStoreFactory) {
        return videoDataStoreFactory;
    }

    @Provides
    VideoRepository providesVideoRepository(VideoDataRepository videoDataRepository) {
        return videoDataRepository;
    }
}
