package com.sftelehealth.doctor.data.repository.datastore;


import com.sftelehealth.doctor.data.model.response.MediaChannelResponse;

import io.reactivex.Observable;

public interface VideoDataStore {

    Observable<MediaChannelResponse> getMediaChannelKey(String channelName, String uid, String patientUserId);
}
