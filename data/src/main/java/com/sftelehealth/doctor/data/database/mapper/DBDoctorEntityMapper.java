package com.sftelehealth.doctor.data.database.mapper;

import com.google.gson.Gson;
import com.sftelehealth.doctor.data.database.entity.Doctor;

/**
 * Created by Rahul on 26/12/17.
 */

public class DBDoctorEntityMapper {

    Gson gson = new Gson();

    public DBDoctorEntityMapper() {
        gson = new Gson();
    }

    public Doctor transform(com.sftelehealth.doctor.data.model.Doctor doctor) {

        if(doctor.getIsdCode() != null)
            doctor.setPhone(doctor.getIsdCode() + doctor.getPhone());

        return gson.fromJson(gson.toJson(doctor), Doctor.class);
    }

    public com.sftelehealth.doctor.data.model.Doctor transformToData(Doctor doctor) {
        return gson.fromJson(gson.toJson(doctor), com.sftelehealth.doctor.data.model.Doctor.class);
    }
}
