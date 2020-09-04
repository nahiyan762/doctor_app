package com.sftelehealth.doctor.data.repository.datastore;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.data.model.CasePrescriptionsAndDocuments;
import com.sftelehealth.doctor.data.model.MedicineType;
import com.sftelehealth.doctor.data.model.Prescription;
import com.sftelehealth.doctor.data.model.request.PrescriptionRequest;
import com.sftelehealth.doctor.data.net.AuthCodeProvider;
import com.sftelehealth.doctor.data.net.CoreApi;
import com.sftelehealth.doctor.data.net.RetrofitService;
import io.reactivex.Observable;

/**
 * Created by Rahul on 02/01/18.
 */

public class PrescriptionDataStoreFactory implements PrescriptionDataStore {

    private final Context context;
    boolean isUserRegistered;
    boolean isCached = false;

    RetrofitService restService = new RetrofitService();
    CoreApi coreApi = restService.getCoreApiService();

    @Inject
    public PrescriptionDataStoreFactory(Context context) {
        this.context = context;
    }


    @Override
    public Observable<CasePrescriptionsAndDocuments> getPrescriptionsAndDocumentsList(Map<String, String> options) {
        HashMap<String, Integer> params = new HashMap<>();
        params.put("caseId", Integer.parseInt(options.get("caseId")));

        return coreApi.getPrescriptionsList(AuthCodeProvider.getAuthCode(context), params).map(casePrescriptionsAndDocumentsResponse -> casePrescriptionsAndDocumentsResponse.getPrescriptionsAndDocuments());
    }

    @Override
    public Observable<Prescription> createPrescription(Prescription prescription) {
        PrescriptionRequest request = new PrescriptionRequest();
        request.setAdvice(prescription.getAdvice());
        request.setHistory(prescription.getHistory());
        request.setMedicine(prescription.getMedicine());
        request.setConsultId(prescription.getConsultId());

        return coreApi.createPrescription(AuthCodeProvider.getAuthCode(context), request);
    }

    @Override
    public Observable<List<MedicineType>> getMedicineType() {
        return coreApi.getMedicineTypes(AuthCodeProvider.getAuthCode(context));
    }


}
