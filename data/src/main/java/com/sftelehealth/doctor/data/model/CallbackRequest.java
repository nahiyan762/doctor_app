package com.sftelehealth.doctor.data.model;

import com.sftelehealth.doctor.domain.model.Document;

import java.util.ArrayList;

/**
 * Created by Rahul on 19/06/17.
 */

public class CallbackRequest {

    private Integer callbackId;
    private String status;
    private Boolean isFollowUp;
    private Integer caseId;
    private String consultedAt;
    private String createdAt;
    private String autoRejectAt;
    private String autoExpireAt;
    private String patientName;
    private String patientImage;
    private String gender;
    private String dob;
    private Patient patient;
    private boolean isVideo;
    private boolean showDialer;
    private String videoStartTime;
    private String videoEndTime;
    private ArrayList<Document> docs;
    private boolean allowVideoFollowUp;

    public Integer getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(Integer callbackId) {
        this.callbackId = callbackId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getFollowUp() {
        return isFollowUp;
    }

    public void setFollowUp(Boolean followUp) {
        isFollowUp = followUp;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getVideoStartTime() {
        return videoStartTime;
    }

    public String getVideoEndTime() {
        return videoEndTime;
    }

    public boolean isShowDialer() {
        return showDialer;
    }

    public void setShowDialer(boolean showDialer) {
        this.showDialer = showDialer;
    }

    public void setVideoStartTime(String videoStartTime) {
        this.videoStartTime = videoStartTime;
    }

    public void setVideoEndTime(String videoEndTime) {
        this.videoEndTime = videoEndTime;
    }

    public ArrayList<Document> getDocs() {
        return docs;
    }

    public boolean isAllowVideoFollowUp() {
        return allowVideoFollowUp;
    }

    public void setAllowVideoFollowUp(boolean allowVideoFollowUp) {
        this.allowVideoFollowUp = allowVideoFollowUp;
    }
}
