package com.sftelehealth.doctor.data.repository.datastore;

import com.sftelehealth.doctor.data.model.request.MediaChannelKeyRequest;
import com.sftelehealth.doctor.data.model.response.MediaChannelResponse;

import io.reactivex.Observable;

public interface MediaDataStore {

    Observable<MediaChannelResponse> getMediaChannelKey(MediaChannelKeyRequest request);
}
