package com.sftelehealth.doctor.data.repository.datastore;

import java.util.List;
import java.util.Map;

import com.sftelehealth.doctor.data.model.CasePrescriptionsAndDocuments;
import com.sftelehealth.doctor.data.model.MedicineType;
import com.sftelehealth.doctor.data.model.Prescription;
import io.reactivex.Observable;

/**
 * Created by Rahul on 27/12/17.
 */

public interface PrescriptionDataStore {

    Observable<CasePrescriptionsAndDocuments> getPrescriptionsAndDocumentsList(Map<String, String> params);

    Observable<Prescription> createPrescription(Prescription prescription);

    Observable<List<MedicineType>> getMedicineType();
}
