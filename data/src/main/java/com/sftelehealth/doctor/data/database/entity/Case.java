package com.sftelehealth.doctor.data.database.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sftelehealth.doctor.data.model.Patient;

/**
 * Created by Rahul on 05/12/17.
 */
@Entity
public class Case {

    @PrimaryKey
    int caseId;
    @Embedded
    Patient patient;
    String patientName;
    String patientImage;
    String lastCall;
    int callbackId;
    int isCallback;
    int hasPrescription;
    int lastConusltId;
    private boolean isFollowUp;
    private boolean freeFollowUp;

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientImage() {
        return patientImage;
    }

    public void setPatientImage(String patientImage) {
        this.patientImage = patientImage;
    }

    public String getLastCall() {
        return lastCall;
    }

    public void setLastCall(String lastCall) {
        this.lastCall = lastCall;
    }

    public int getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(int callbackId) {
        this.callbackId = callbackId;
    }

    public int getIsCallback() {
        return isCallback;
    }

    public void setIsCallback(int isCallback) {
        this.isCallback = isCallback;
    }

    public int getHasPrescription() {
        return hasPrescription;
    }

    public void setHasPrescription(int hasPrescription) {
        this.hasPrescription = hasPrescription;
    }

    public int getLastConusltId() {
        return lastConusltId;
    }

    public void setLastConusltId(int lastConusltId) {
        this.lastConusltId = lastConusltId;
    }

    public boolean isFollowUp() {
        return isFollowUp;
    }

    public void setFollowUp(boolean followUp) {
        isFollowUp = followUp;
    }

    public boolean isFreeFollowUp() {
        return freeFollowUp;
    }

    public void setFreeFollowUp(boolean freeFollowUp) {
        this.freeFollowUp = freeFollowUp;
    }
}
