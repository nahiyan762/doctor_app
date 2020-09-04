package com.sftelehealth.doctor.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.model.response.ShouldUpdateResponse;
import com.sftelehealth.doctor.domain.repository.SystemRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 11/02/18.
 */

public class ShouldUpdateApp extends UseCase<ShouldUpdateResponse, Map<String, String>> {

    SystemRepository systemRepository;

    @Inject
    ShouldUpdateApp(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SystemRepository systemRepository) {
        super(threadExecutor, postExecutionThread);
        this.systemRepository = systemRepository;
    }

    @Override
    Observable<ShouldUpdateResponse> buildUseCaseObservable(Map<String, String> stringStringMap) {
        return systemRepository.shouldUpdate();
    }
}
