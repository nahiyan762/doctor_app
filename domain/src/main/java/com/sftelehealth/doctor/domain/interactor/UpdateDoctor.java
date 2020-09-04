package com.sftelehealth.doctor.domain.interactor;

import java.util.HashMap;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.model.response.UpdateDoctorResponse;
import com.sftelehealth.doctor.domain.repository.SystemRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 13/03/18.
 */

public class UpdateDoctor extends UseCase<UpdateDoctorResponse, HashMap<String, String>> {

    SystemRepository systemRepository;

    @Inject
    UpdateDoctor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SystemRepository systemRepository) {
        super(threadExecutor, postExecutionThread);
        this.systemRepository = systemRepository;
    }

    @Override
    Observable<UpdateDoctorResponse> buildUseCaseObservable(HashMap<String, String> params) {
        return systemRepository.updateDoctor(params);
    }
}
