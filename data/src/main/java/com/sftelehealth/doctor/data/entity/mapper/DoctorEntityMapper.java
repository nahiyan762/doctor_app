package com.sftelehealth.doctor.data.entity.mapper;

import com.google.gson.Gson;

import com.sftelehealth.doctor.domain.model.Doctor;


/**
 * Created by Rahul on 25/12/17.
 */

public class DoctorEntityMapper {

    Gson gson;

    public DoctorEntityMapper() {
        gson = new Gson();
    }


    public Doctor transform (com.sftelehealth.doctor.data.model.Doctor doctorTemp) {

        return gson.fromJson(gson.toJson(doctorTemp), Doctor.class);
    }

    public Doctor transformToDomainEntity(com.sftelehealth.doctor.data.model.Doctor doctor) {
        return gson.fromJson(gson.toJson(doctor), Doctor.class);
    }
}
