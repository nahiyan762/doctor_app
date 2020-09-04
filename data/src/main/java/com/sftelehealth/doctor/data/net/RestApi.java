package com.sftelehealth.doctor.data.net;

import com.sftelehealth.doctor.data.model.request.MediaChannelKeyRequest;
import com.sftelehealth.doctor.data.model.response.MediaChannelResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by rahul on 08/08/16.
 */
public interface RestApi {

    /**
     * Get the MediaChannelKey to join the communication channel unique for a given callback
     * @param {@link MediaChannelKeyRequest} - "channelName", "uid", "expiredTs" = 0, "userId"
     * @return {@link MediaChannelResponse}
     */
    @POST("patient/getMediaChannelKey")
    Observable<MediaChannelResponse> getMediaChannelKey(
            @Body MediaChannelKeyRequest mediaChannelKeyRequest
    );
}