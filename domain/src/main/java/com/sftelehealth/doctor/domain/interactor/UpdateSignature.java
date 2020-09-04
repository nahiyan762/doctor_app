package com.sftelehealth.doctor.domain.interactor;

import java.util.HashMap;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.repository.DoctorRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 25/01/18.
 */

public class UpdateSignature extends UseCase<String, HashMap<String, String>> {

    DoctorRepository doctorRepository;

    @Inject
    UpdateSignature(DoctorRepository doctorRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.doctorRepository = doctorRepository;
    }

    @Override
    Observable<String> buildUseCaseObservable(HashMap<String, String> params) {
        return doctorRepository.doctorSignatureUpdate(params);
    }
}