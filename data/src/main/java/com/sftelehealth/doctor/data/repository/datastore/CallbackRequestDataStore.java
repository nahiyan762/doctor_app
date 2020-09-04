package com.sftelehealth.doctor.data.repository.datastore;

import java.util.List;
import java.util.Map;

import com.sftelehealth.doctor.data.model.CallbackRequest;
import com.sftelehealth.doctor.data.model.response.StartDoctorCallResponse;
import io.reactivex.Observable;

/**
 * Created by Rahul on 28/12/17.
 */

public interface CallbackRequestDataStore {

    Observable<List<CallbackRequest>> getCallbackRequests (Map<String, String> params);

    Observable<CallbackRequest> acceptRejectCallback(int callbackId, boolean callbackAccepted);

    Observable<StartDoctorCallResponse> initiateCallback(int callbackId);
}
