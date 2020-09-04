package com.sftelehealth.doctor.data.repository.datastore;

import android.content.Context;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.data.model.CallbackRequest;
import com.sftelehealth.doctor.data.net.CoreApi;
import com.sftelehealth.doctor.data.net.RetrofitService;
import io.reactivex.Observable;

/**
 * Created by Rahul on 27/12/17.
 */

public class AppointmentDataStoreFactory implements AppointmentDataStore {

    private final Context context;
    boolean isUserRegistered;
    boolean isCached = false;

    RetrofitService restService = new RetrofitService();
    CoreApi coreApi = restService.getCoreApiService();

    @Inject
    public AppointmentDataStoreFactory(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<CallbackRequest>> getAppointments(String authCode, Map<String, String> params) {
        return coreApi.getAppointments(authCode, params).map(callbackRequestResponse -> callbackRequestResponse.getRequests());
    }
}
