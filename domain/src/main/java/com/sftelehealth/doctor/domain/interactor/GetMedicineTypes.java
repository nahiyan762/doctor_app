package com.sftelehealth.doctor.domain.interactor;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.model.MedicineType;
import com.sftelehealth.doctor.domain.repository.PrescriptionRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 24/01/18.
 */

public class GetMedicineTypes extends UseCase<List<MedicineType>, Map<String, String>> {

    private final PrescriptionRepository prescriptionRepository;

    @Inject
    GetMedicineTypes(PrescriptionRepository prescriptionRepository, ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    Observable<List<MedicineType>> buildUseCaseObservable(Map<String, String> stringStringMap) {
        return prescriptionRepository.getMedicineType();
    }
}
