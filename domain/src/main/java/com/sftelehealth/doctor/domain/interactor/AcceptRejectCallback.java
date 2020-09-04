package com.sftelehealth.doctor.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.model.CallbackRequest;
import com.sftelehealth.doctor.domain.repository.CallbackRequestRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 31/12/17.
 */

public class AcceptRejectCallback extends UseCase<CallbackRequest, Map<String, String>>{

    CallbackRequestRepository callbackRequestRepository;

    @Inject
    AcceptRejectCallback(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, CallbackRequestRepository callbackRequestRepository) {
        super(threadExecutor, postExecutionThread);

        this.callbackRequestRepository = callbackRequestRepository;
    }

    @Override
    Observable<CallbackRequest> buildUseCaseObservable(Map<String, String> params) {
        return callbackRequestRepository.acceptRejectCallback(Integer.parseInt(params.get("callback_id")), Boolean.parseBoolean(params.get("callback_accepted")));
    }
}
