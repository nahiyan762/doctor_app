package com.sftelehealth.doctor.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.model.CasePrescriptionsAndDocuments;
import com.sftelehealth.doctor.domain.repository.PrescriptionRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 02/01/18.
 */

public class GetPrescriptionAndDocumentsForCase extends UseCase<CasePrescriptionsAndDocuments, Map<String, String>> {

    private final PrescriptionRepository prescriptionRepository;

    @Inject
    GetPrescriptionAndDocumentsForCase(PrescriptionRepository prescriptionRepository, ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public Observable<CasePrescriptionsAndDocuments> buildUseCaseObservable(Map<String, String> params) {
        return prescriptionRepository.getPrescriptionsList(params);
    }

    public Observable<CasePrescriptionsAndDocuments> getObservable(Map<String, String> params) {
        return prescriptionRepository.getPrescriptionsList(params);
    }
}
