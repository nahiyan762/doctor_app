package com.sftelehealth.doctor.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.sftelehealth.doctor.data.model.MedicineType;

/**
 * Created by Rahul on 24/01/18.
 */

public class MedicineTypeEntityMapper {

    Gson gson;

    public MedicineTypeEntityMapper() {
        gson = new Gson();
    }

    public List<com.sftelehealth.doctor.domain.model.MedicineType> transform (List<MedicineType> medicineTypes) {

        Type listType = new TypeToken<ArrayList<com.sftelehealth.doctor.domain.model.MedicineType>>(){}.getType();
        return gson.fromJson(gson.toJson(medicineTypes), listType);
    }
}
