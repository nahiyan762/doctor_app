package com.sftelehealth.doctor.data.repository.datastore;

import android.content.Context;

import com.sftelehealth.doctor.data.model.request.MediaChannelKeyRequest;
import com.sftelehealth.doctor.data.model.response.MediaChannelResponse;
import com.sftelehealth.doctor.data.net.RestApi;
import com.sftelehealth.doctor.data.net.RetrofitService;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MediaDataStoreFactory implements MediaDataStore {

    private final Context context;
    boolean isUserRegistered;
    boolean isCached = false;

    RetrofitService restService = new RetrofitService();
    RestApi restApi = restService.getRestApiService();

    @Inject
    public MediaDataStoreFactory(Context context) {
        this.context = context;
    }

    @Override
    public Observable<MediaChannelResponse> getMediaChannelKey(MediaChannelKeyRequest request) {
        return restApi.getMediaChannelKey(request);
    }
}