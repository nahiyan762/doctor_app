package com.sftelehealth.doctor.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.model.Case;
import com.sftelehealth.doctor.domain.repository.CasesRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 02/01/18.
 */

public class GetCaseById extends UseCase<Case, Map<String, String>>{


    private final CasesRepository casesRepository;

    @Inject
    GetCaseById(CasesRepository casesRepository, ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.casesRepository = casesRepository;
    }

    @Override
    Observable<Case> buildUseCaseObservable(Map<String, String> params) {
        return casesRepository.getCasesById(params);
    }

    public Observable<Case> getObservable(Map<String, String> params) {
        return casesRepository.getCasesById(params);
    }
}
