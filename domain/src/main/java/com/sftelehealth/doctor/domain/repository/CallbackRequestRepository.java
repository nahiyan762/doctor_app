package com.sftelehealth.doctor.domain.repository;

import java.util.List;
import java.util.Map;

import com.sftelehealth.doctor.domain.model.CallbackRequest;
import io.reactivex.Observable;

/**
 * Created by Rahul on 20/06/17.
 */

public interface CallbackRequestRepository {

    /**
     * Get an {@link Observable} which will emit a List of {@link CallbackRequest}.
     */
    Observable<List<CallbackRequest>> getCallbacks(Map<String, String> options);

    Observable<CallbackRequest> getCallbackById(Map<String, String> options);

    Observable<CallbackRequest> acceptRejectCallback(int callbackId, boolean callbackAccepted);

    Observable<Boolean> initiateCallback(int callbackId);

}
