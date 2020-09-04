package com.sftelehealth.doctor.data.database.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sftelehealth.doctor.data.model.Patient;

/**
 * Created by Rahul on 04/12/17.
 */
@Entity
public class Appointment {

    @PrimaryKey
    int callbackId;

    String status;
    boolean isFollowUp;
    int caseId;
    boolean isVideo;
    String consultedAt;
    String createdAt;
    String autoRejectAt;
    String autoExpireAt;
    @Embedded
    Patient patient;
    //int userId;

    public int getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(int callbackId) {
        this.callbackId = callbackId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFollowUp() {
        return isFollowUp;
    }

    public void setFollowUp(boolean followUp) {
        isFollowUp = followUp;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getConsultedAt() {
        return consultedAt;
    }

    public void setConsultedAt(String consultedAt) {
        this.consultedAt = consultedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAutoRejectAt() {
        return autoRejectAt;
    }

    public void setAutoRejectAt(String autoRejectAt) {
        this.autoRejectAt = autoRejectAt;
    }

    public String getAutoExpireAt() {
        return autoExpireAt;
    }

    public void setAutoExpireAt(String autoExpireAt) {
        this.autoExpireAt = autoExpireAt;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /*public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }*/
}
