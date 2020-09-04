package com.sftelehealth.doctor.data.entity.mapper;

import com.google.gson.Gson;
import com.sftelehealth.doctor.data.model.CasePrescriptionsAndDocuments;

/**
 * Created by Rahul on 02/01/18.
 */

public class PrescriptionListEntityMapper {

    Gson gson;

    public PrescriptionListEntityMapper() {
        gson = new Gson();
    }

    public com.sftelehealth.doctor.domain.model.CasePrescriptionsAndDocuments transform (CasePrescriptionsAndDocuments doctorTemp) {

        //Type listType = new TypeToken<ArrayList<com.traktion.doctor.domain.model.CallbackRequest>>(){}.getType();
        return gson.fromJson(gson.toJson(doctorTemp), com.sftelehealth.doctor.domain.model.CasePrescriptionsAndDocuments.class);
    }
}
