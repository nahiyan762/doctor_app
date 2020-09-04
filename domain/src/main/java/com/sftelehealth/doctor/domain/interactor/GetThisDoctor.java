package com.sftelehealth.doctor.domain.interactor;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.model.Doctor;
import com.sftelehealth.doctor.domain.repository.DoctorRepository;

import io.reactivex.Observable;

/**
 * Created by Rahul on 16/01/18.
 */

public class GetThisDoctor extends UseCase<Doctor, String> {

    DoctorRepository doctorRepository;

    @Inject
    GetThisDoctor(DoctorRepository doctorRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.doctorRepository = doctorRepository;
    }

    @Override
    Observable<Doctor> buildUseCaseObservable(String s) {
        return doctorRepository.getDoctor();
    }

    public Observable<Doctor> getObservable (String s) {
        return doctorRepository.getDoctor();
    }
}
