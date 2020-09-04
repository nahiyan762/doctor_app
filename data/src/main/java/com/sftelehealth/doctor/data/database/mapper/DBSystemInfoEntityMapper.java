package com.sftelehealth.doctor.data.database.mapper;

import com.google.gson.Gson;
import com.sftelehealth.doctor.data.database.entity.SystemInfo;

public class DBSystemInfoEntityMapper {

    Gson gson = new Gson();

    public DBSystemInfoEntityMapper() {
        gson = new Gson();
    }

    public SystemInfo transform(com.sftelehealth.doctor.data.model.response.ShouldUpdateResponse doctor) {

        return gson.fromJson(gson.toJson(doctor), SystemInfo.class);
    }

    public com.sftelehealth.doctor.data.model.response.ShouldUpdateResponse transformToData(SystemInfo doctor) {
        return gson.fromJson(gson.toJson(doctor), com.sftelehealth.doctor.data.model.response.ShouldUpdateResponse.class);
    }
}
