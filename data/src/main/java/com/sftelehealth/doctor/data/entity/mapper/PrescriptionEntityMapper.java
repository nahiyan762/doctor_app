package com.sftelehealth.doctor.data.entity.mapper;

import android.util.Log;

import com.google.gson.Gson;

import com.google.gson.JsonSyntaxException;
import com.sftelehealth.doctor.data.model.Prescription;

/**
 * Created by Rahul on 23/01/18.
 */

public class PrescriptionEntityMapper {

    Gson gson;

    public PrescriptionEntityMapper() {
        gson = new Gson();
    }


    public Prescription transform (com.sftelehealth.doctor.domain.model.Prescription prescription) {

        try {
            return gson.fromJson(gson.toJson(prescription), Prescription.class);
        } catch (JsonSyntaxException e) {
            Log.e("Prescription Exception", e.getMessage());
        } catch (NumberFormatException e) {
            Log.e("Prescription Exception", e.getMessage());
        }

        return null;
    }

    public com.sftelehealth.doctor.domain.model.Prescription transformToDomainEntity(Prescription prescription) {
        return gson.fromJson(gson.toJson(prescription), com.sftelehealth.doctor.domain.model.Prescription.class);
    }
}
