package com.sftelehealth.doctor.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.sftelehealth.doctor.data.model.CallbackRequest;

/**
 * Created by Rahul on 28/12/17.
 */

public class AppointmentEntityMapper {

    Gson gson;

    public AppointmentEntityMapper() {
        gson = new Gson();
    }

    public List<com.sftelehealth.doctor.domain.model.CallbackRequest> transform (List<CallbackRequest> callbackRequest) {

        Type listType = new TypeToken<ArrayList<com.sftelehealth.doctor.domain.model.CallbackRequest>>(){}.getType();
        return gson.fromJson(gson.toJson(callbackRequest), listType);
    }

    public com.sftelehealth.doctor.domain.model.CallbackRequest transform (CallbackRequest callbackRequest) {

        return gson.fromJson(gson.toJson(callbackRequest), com.sftelehealth.doctor.domain.model.CallbackRequest.class);
    }
}
