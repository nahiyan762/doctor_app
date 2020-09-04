package com.sftelehealth.doctor.domain.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.sftelehealth.doctor.domain.model.CasePrescriptionsAndDocuments;


/**
 * Created by Rahul on 19/06/17.
 */

public class CasePrescriptionsAndDocumentsResponse {

    @SerializedName("caseDetails")
    @Expose
    private CasePrescriptionsAndDocuments prescriptionsAndDocuments;

    public CasePrescriptionsAndDocuments getPrescriptionsAndDocuments() {
        return prescriptionsAndDocuments;
    }

    public void setPrescriptionsAndDocuments(CasePrescriptionsAndDocuments prescriptionsAndDocuments) {
        this.prescriptionsAndDocuments = prescriptionsAndDocuments;
    }
}
