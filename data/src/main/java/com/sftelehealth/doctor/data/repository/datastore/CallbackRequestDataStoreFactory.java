package com.sftelehealth.doctor.data.repository.datastore;

import android.content.Context;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.data.model.CallbackRequest;
import com.sftelehealth.doctor.data.model.request.CallbackUpdateRequest;
import com.sftelehealth.doctor.data.model.response.StartDoctorCallResponse;
import com.sftelehealth.doctor.data.net.AuthCodeProvider;
import com.sftelehealth.doctor.data.net.CoreApi;
import com.sftelehealth.doctor.data.net.RetrofitService;
import io.reactivex.Observable;

/**
 * Created by Rahul on 28/12/17.
 */

public class CallbackRequestDataStoreFactory implements CallbackRequestDataStore {

    private final Context context;
    boolean isUserRegistered;
    boolean isCached = false;

    RetrofitService restService = new RetrofitService();
    CoreApi coreApi = restService.getCoreApiService();

    @Inject
    public CallbackRequestDataStoreFactory(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<CallbackRequest>> getCallbackRequests(Map<String, String> params) {
        return coreApi.getAppointments(AuthCodeProvider.getAuthCode(context), params)
                .map(callbackRequestResponse -> {
            return callbackRequestResponse.getRequests();
        });
    }

    @Override
    public Observable<CallbackRequest> acceptRejectCallback(int callbackId, boolean callbackAccepted) {

        CallbackUpdateRequest request = new CallbackUpdateRequest();
        request.setCallbackId(callbackId);

        if(callbackAccepted) {
            return coreApi.acceptAppointment(AuthCodeProvider.getAuthCode(context), request).map(callbackResponse -> callbackResponse.get(0));
        } else {
            return coreApi.cancelAppointment(AuthCodeProvider.getAuthCode(context), request).map(callbackResponse -> {
                CallbackRequest response = new CallbackRequest();
                response.setCallbackId(callbackResponse.getId());
                return response;
            });
        }
    }

    @Override
    public Observable<StartDoctorCallResponse> initiateCallback(int callbackId) {

        CallbackUpdateRequest request = new CallbackUpdateRequest();
        request.setCallbackId(callbackId);

        return coreApi.startDoctorCall(AuthCodeProvider.getAuthCode(context), request);
    }
}
