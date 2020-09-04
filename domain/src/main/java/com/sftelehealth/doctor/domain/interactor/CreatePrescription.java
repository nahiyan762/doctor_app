package com.sftelehealth.doctor.domain.interactor;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.model.Prescription;
import com.sftelehealth.doctor.domain.repository.PrescriptionRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 23/01/18.
 */

public class CreatePrescription extends UseCase<Prescription, Prescription> {

    private final PrescriptionRepository prescriptionRepository;

    @Inject
    CreatePrescription(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PrescriptionRepository prescriptionRepository) {
        super(threadExecutor, postExecutionThread);
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    Observable<Prescription> buildUseCaseObservable(Prescription prescription) {
        return prescriptionRepository.createPrescription(prescription);
    }
}
