package com.sftelehealth.doctor.domain.interactor;

import java.util.HashMap;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.repository.DoctorRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 24/01/18.
 */

public class UpdateDoctorImage extends UseCase<String, HashMap<String, String>> {

    DoctorRepository doctorRepository;

    @Inject
    UpdateDoctorImage(DoctorRepository doctorRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.doctorRepository = doctorRepository;
    }

    @Override
    Observable<String> buildUseCaseObservable(HashMap<String, String> params) {
        return doctorRepository.doctorImageUpdate(params);
    }
}
