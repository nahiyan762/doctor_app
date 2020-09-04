package com.sftelehealth.doctor.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.model.Doctor;
import com.sftelehealth.doctor.domain.repository.SystemRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 21/06/17.
 */
public class ConfirmOtp extends UseCase<Doctor, Map<String, String>> {

    private final SystemRepository systemCallRepository;

    @Inject
    ConfirmOtp(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SystemRepository systemCallRepository) {
        super(threadExecutor, postExecutionThread);
        this.systemCallRepository = systemCallRepository;
    }


    @Override
    Observable<Doctor> buildUseCaseObservable(Map<String, String> params) {
        return systemCallRepository.doctorVerify(params.get("phone"), params.get("otp"), params.get("version_name"));
    }
}
