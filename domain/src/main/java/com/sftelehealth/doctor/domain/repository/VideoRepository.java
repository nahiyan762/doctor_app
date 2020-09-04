package com.sftelehealth.doctor.domain.repository;

import com.sftelehealth.doctor.domain.model.response.MediaChannelResponse;

import io.reactivex.Observable;

public interface VideoRepository {

    Observable<MediaChannelResponse> getMediaChannelKey(String channelName, String uid, String patientUserId);
}
