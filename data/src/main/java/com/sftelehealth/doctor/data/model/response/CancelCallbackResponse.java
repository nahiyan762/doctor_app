package com.sftelehealth.doctor.data.model.response;

/**
 * Created by Rahul on 08/03/18.
 */

public class CancelCallbackResponse {

    int doctorId, patientId, callLogId, attempts, id;
    String caseId, status, consultedAt, autoExpireAt, autoRejectAt, createdAt, updatedAt;
    boolean isVideo;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getCallLogId() {
        return callLogId;
    }

    public void setCallLogId(int callLogId) {
        this.callLogId = callLogId;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConsultedAt() {
        return consultedAt;
    }

    public void setConsultedAt(String consultedAt) {
        this.consultedAt = consultedAt;
    }

    public String getAutoExpireAt() {
        return autoExpireAt;
    }

    public void setAutoExpireAt(String autoExpireAt) {
        this.autoExpireAt = autoExpireAt;
    }

    public String getAutoRejectAt() {
        return autoRejectAt;
    }

    public void setAutoRejectAt(String autoRejectAt) {
        this.autoRejectAt = autoRejectAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }
}
