package com.sftelehealth.doctor.data.repository;

import com.sftelehealth.doctor.data.repository.datastore.VideoDataStore;
import com.sftelehealth.doctor.domain.model.response.MediaChannelResponse;
import com.sftelehealth.doctor.domain.repository.VideoRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class VideoDataRepository implements VideoRepository {

    private final VideoDataStore videoDataStore;

    @Inject
    VideoDataRepository(VideoDataStore videoDataStore) {
        this.videoDataStore = videoDataStore;
    }

    @Override
    public Observable<MediaChannelResponse> getMediaChannelKey(String channelName, String uid, String userId) {
        return videoDataStore.getMediaChannelKey(channelName, uid, userId).map(response -> {
            MediaChannelResponse mediaChannelResponse = new MediaChannelResponse();
            mediaChannelResponse.setMediaChannelKey(response.getMediaChannelKey());
            return mediaChannelResponse;
        });
    }
}
