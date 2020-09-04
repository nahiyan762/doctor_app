package com.sftelehealth.doctor.domain.interactor;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.model.SystemInfo;
import com.sftelehealth.doctor.domain.repository.SystemRepository;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetSystemInfo extends UseCase<SystemInfo, Map<String, String>> {

    private final SystemRepository systemCallRepository;

    @Inject
    GetSystemInfo(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SystemRepository systemCallRepository) {
        super(threadExecutor, postExecutionThread);
        this.systemCallRepository = systemCallRepository;
    }

    @Override
    Observable<SystemInfo> buildUseCaseObservable(Map<String, String> stringStringMap) {
        return systemCallRepository.getSystemInfoFromPersistence();
    }
}
