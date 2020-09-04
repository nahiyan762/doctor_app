package com.sftelehealth.doctor.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.repository.CallbackRequestRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 31/12/17.
 */

public class InitiateCall extends UseCase<Boolean, Map<String, String>> {

    private final CallbackRequestRepository callbackRequestRepository;

    @Inject
    InitiateCall(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, CallbackRequestRepository callbackRequestRepository) {
        super(threadExecutor, postExecutionThread);

        this.callbackRequestRepository = callbackRequestRepository;
    }

    @Override
    Observable<Boolean> buildUseCaseObservable(Map<String, String> params) {
        return callbackRequestRepository.initiateCallback(Integer.parseInt(params.get("callback_id")));
    }
}
