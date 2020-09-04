package com.sftelehealth.doctor.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.model.response.IsUserAuthenticatedResponse;
import com.sftelehealth.doctor.domain.repository.SystemRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 06/01/18.
 */

public class IsUserAuthenticated extends UseCase<IsUserAuthenticatedResponse, Map<String, String>> {

    SystemRepository systemRepository;

    @Inject
    IsUserAuthenticated(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SystemRepository systemRepository) {
        super(threadExecutor, postExecutionThread);
        this.systemRepository = systemRepository;
    }

    @Override
    Observable<IsUserAuthenticatedResponse> buildUseCaseObservable(Map<String, String> stringStringMap) {
        return systemRepository.isUserAuthenticated();
    }
}