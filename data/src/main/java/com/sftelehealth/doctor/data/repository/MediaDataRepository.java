package com.sftelehealth.doctor.data.repository;

import com.sftelehealth.doctor.domain.model.response.MediaChannelResponse;
import com.sftelehealth.doctor.domain.repository.MediaRepository;

import io.reactivex.Observable;

public class MediaDataRepository implements MediaRepository{

    @Override
    public Observable<MediaChannelResponse> getMediaChannelKey() {
        return null;
    }
}