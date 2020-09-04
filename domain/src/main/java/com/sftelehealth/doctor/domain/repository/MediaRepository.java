package com.sftelehealth.doctor.domain.repository;

import com.sftelehealth.doctor.domain.model.response.MediaChannelResponse;
import io.reactivex.Observable;

public interface MediaRepository {

    Observable<MediaChannelResponse> getMediaChannelKey();
}
