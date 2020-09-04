package com.sftelehealth.doctor.domain.interactor;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.model.Case;
import com.sftelehealth.doctor.domain.repository.CasesRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 20/06/17.
 */

public class GetCaseList extends UseCase<List<Case>, Map<String, String>> {

    private final CasesRepository casesRepository;

    @Inject
    GetCaseList(CasesRepository casesRepository, ThreadExecutor threadExecutor,
             PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.casesRepository = casesRepository;
    }

    @Override
    Observable<List<Case>> buildUseCaseObservable(Map<String, String> params) {
        return casesRepository.getCases(params);
    }
}
