package com.sftelehealth.doctor.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.sftelehealth.doctor.data.model.Case;

/**
 * Created by Rahul on 26/12/17.
 */

public class CaseEntityMapper {

    Gson gson;

    public CaseEntityMapper() {
        gson = new Gson();
    }


    public List<com.sftelehealth.doctor.domain.model.Case> transform (List<Case> caseTemp) {

        Type listType = new TypeToken<ArrayList<com.sftelehealth.doctor.domain.model.Case>>(){}.getType();
        return gson.fromJson(gson.toJson(caseTemp), listType);
    }

    public com.sftelehealth.doctor.domain.model.Case transform (Case caseTemp) {

        //Type listType = new TypeToken<ArrayList<com.traktion.doctor.domain.model.Case>>(){}.getType();
        return gson.fromJson(gson.toJson(caseTemp), com.sftelehealth.doctor.domain.model.Case.class);
    }
}
