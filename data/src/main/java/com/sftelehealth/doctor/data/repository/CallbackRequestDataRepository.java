package com.sftelehealth.doctor.data.repository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.data.entity.mapper.AppointmentEntityMapper;
import com.sftelehealth.doctor.data.repository.datastore.CallbackRequestDataStore;
import com.sftelehealth.doctor.domain.model.CallbackRequest;
import com.sftelehealth.doctor.domain.repository.CallbackRequestRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 28/12/17.
 */

public class CallbackRequestDataRepository implements CallbackRequestRepository {

    private final CallbackRequestDataStore callbackRequestDataStore;

    @Inject
    CallbackRequestDataRepository(CallbackRequestDataStore callbackRequestDataStore) {
        this.callbackRequestDataStore = callbackRequestDataStore;
    }

    @Override
    public Observable<List<CallbackRequest>> getCallbacks(Map<String, String> options) {
        return callbackRequestDataStore.getCallbackRequests(options).map(callbackRequestList -> {

            AppointmentEntityMapper mapper = new AppointmentEntityMapper();
            return mapper.transform(callbackRequestList);

        });
    }

    @Override
    public Observable<CallbackRequest> getCallbackById(Map<String, String> options) {
        return callbackRequestDataStore.getCallbackRequests(options).map(callbackRequests -> {
            AppointmentEntityMapper mapper = new AppointmentEntityMapper();
            return mapper.transform(callbackRequests).get(0);
        });
    }

    @Override
    public Observable<CallbackRequest> acceptRejectCallback(int callbackId, boolean callbackAccepted) {
        return callbackRequestDataStore.acceptRejectCallback(callbackId, callbackAccepted).map(callbackRequest -> {
            AppointmentEntityMapper mapper = new AppointmentEntityMapper();
            return mapper.transform(callbackRequest);
        });
    }

    @Override
    public Observable<Boolean> initiateCallback(int callbackId) {
        return callbackRequestDataStore.initiateCallback(callbackId).map(startDoctorCallResponse -> {return true; });
    }

}
