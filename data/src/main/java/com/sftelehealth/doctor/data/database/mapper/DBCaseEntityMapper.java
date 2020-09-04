package com.sftelehealth.doctor.data.database.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.sftelehealth.doctor.data.model.Case;

/**
 * Created by Rahul on 29/12/17.
 */

public class DBCaseEntityMapper {

    Gson gson;

    public DBCaseEntityMapper() {
        gson = new Gson();
    }


    public List<com.sftelehealth.doctor.data.database.entity.Case> transform (List<Case> doctorTemp) {

        Type listType = new TypeToken<ArrayList<com.sftelehealth.doctor.data.database.entity.Case>>(){}.getType();
        return gson.fromJson(gson.toJson(doctorTemp), listType);
    }

    public com.sftelehealth.doctor.data.model.Case transform (com.sftelehealth.doctor.data.database.entity.Case doctorTemp) {

        //Type listType = new TypeToken<ArrayList<com.traktion.doctor.data.database.entity.Case>>(){}.getType();
        return gson.fromJson(gson.toJson(doctorTemp), com.sftelehealth.doctor.data.model.Case.class);
    }
}
