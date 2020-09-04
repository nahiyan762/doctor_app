package com.sftelehealth.doctor.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.repository.CasesRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 24/01/18.
 */

public class InitiateEmergencyCall extends UseCase<Boolean, Map<String, String>> {

    private final CasesRepository casesRepository;

    @Inject
    InitiateEmergencyCall(CasesRepository casesRepository, ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.casesRepository = casesRepository;
    }

    @Override
    Observable<Boolean> buildUseCaseObservable(Map<String, String> params) {
        return casesRepository.initiateEmergencyCall(params);
    }
}
