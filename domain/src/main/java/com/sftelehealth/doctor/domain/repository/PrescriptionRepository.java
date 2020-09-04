package com.sftelehealth.doctor.domain.repository;

import java.util.List;
import java.util.Map;

import com.sftelehealth.doctor.domain.model.CasePrescriptionsAndDocuments;
import com.sftelehealth.doctor.domain.model.MedicineType;
import com.sftelehealth.doctor.domain.model.Prescription;
import io.reactivex.Observable;

/**
 * Created by Rahul on 20/06/17.
 */

public interface PrescriptionRepository {

    Observable<CasePrescriptionsAndDocuments> getPrescriptionsList(Map<String, String> options);

    Observable<Prescription> createPrescription(Prescription prescription);

    Observable<List<MedicineType>> getMedicineType();
}
