package com.sftelehealth.doctor.data.repository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.data.entity.mapper.MedicineTypeEntityMapper;
import com.sftelehealth.doctor.data.entity.mapper.PrescriptionEntityMapper;
import com.sftelehealth.doctor.data.entity.mapper.PrescriptionListEntityMapper;
import com.sftelehealth.doctor.data.repository.datastore.PrescriptionDataStore;
import com.sftelehealth.doctor.domain.model.MedicineType;
import com.sftelehealth.doctor.domain.model.Prescription;
import com.sftelehealth.doctor.domain.repository.PrescriptionRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 02/01/18.
 */

public class PrescriptionDataRepository implements PrescriptionRepository {

    PrescriptionDataStore prescriptionDataStore;

    @Inject
    PrescriptionDataRepository(PrescriptionDataStore prescriptionDataStore) {
        this.prescriptionDataStore = prescriptionDataStore;
    }

    @Override
    public Observable<com.sftelehealth.doctor.domain.model.CasePrescriptionsAndDocuments> getPrescriptionsList(Map<String, String> options) {
        return prescriptionDataStore.getPrescriptionsAndDocumentsList(options).map(casePrescriptionsAndDocuments -> {
            PrescriptionListEntityMapper mapper = new PrescriptionListEntityMapper();
            return mapper.transform(casePrescriptionsAndDocuments);
        });
    }

    @Override
    public Observable<Prescription> createPrescription(Prescription prescription) {
        PrescriptionEntityMapper mapper = new PrescriptionEntityMapper();
        return prescriptionDataStore.createPrescription(mapper.transform(prescription)).map(prescriptionData -> mapper.transformToDomainEntity(prescriptionData));
    }

    @Override
    public Observable<List<MedicineType>> getMedicineType() {
        MedicineTypeEntityMapper mapper = new MedicineTypeEntityMapper();
        return prescriptionDataStore.getMedicineType().map(medicineTypes -> mapper.transform(medicineTypes));
    }


}
