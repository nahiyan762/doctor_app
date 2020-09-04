package com.sftelehealth.doctor.domain.interactor;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.repository.SystemRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 20/06/17.
 */

public class GenerateOTP extends UseCase<Boolean, String> {

    private final SystemRepository systemRepository;

    @Inject
    GenerateOTP(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SystemRepository systemCallRepository) {
        super(threadExecutor, postExecutionThread);
        this.systemRepository = systemCallRepository;
    }

    @Override
    Observable<Boolean> buildUseCaseObservable(String phone) {
        return systemRepository.checkRegistration(phone);
    }
}
