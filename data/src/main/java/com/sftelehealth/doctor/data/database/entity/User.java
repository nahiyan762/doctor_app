package com.sftelehealth.doctor.data.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Rahul on 04/12/17.
 */

@Entity
public class User {

    @PrimaryKey
    int id;

    String title;
    String firstName;
    String lastName;
    String regNo;
    String signatureImage;
    int doctorCategoryId;
    String qualification;
    int workingSince;
    boolean isDisabled;
    boolean connectNow;
    boolean enableTalk;
    int surfaceCount;
    String email;
    String phone;
    String location;
    String hospital;
    String gender;
    String image;
    boolean allowCallback;
    String phoneOs;
    String phoneOsVersion;
    String oneSignalId;
    String gcmapnsKey;
    String androidId;
    String deviceDescription;
    int appVersion;
    String token;
    boolean uninstalled;
    String uninstallDate;

    String createdAt;
    String updatedAt;
    String languages;
    String doctorCategoryTitle;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getSignatureImage() {
        return signatureImage;
    }

    public void setSignatureImage(String signatureImage) {
        this.signatureImage = signatureImage;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhoneOs() {
        return phoneOs;
    }

    public void setPhoneOs(String phoneOs) {
        this.phoneOs = phoneOs;
    }

    public String getPhoneOsVersion() {
        return phoneOsVersion;
    }

    public void setPhoneOsVersion(String phoneOsVersion) {
        this.phoneOsVersion = phoneOsVersion;
    }

    public String getOneSignalId() {
        return oneSignalId;
    }

    public void setOneSignalId(String oneSignalId) {
        this.oneSignalId = oneSignalId;
    }

    public String getGcmapnsKey() {
        return gcmapnsKey;
    }

    public void setGcmapnsKey(String gcmapnsKey) {
        this.gcmapnsKey = gcmapnsKey;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDoctorCategoryId() {
        return doctorCategoryId;
    }

    public void setDoctorCategoryId(int doctorCategoryId) {
        this.doctorCategoryId = doctorCategoryId;
    }

    public int getWorkingSince() {
        return workingSince;
    }

    public void setWorkingSince(int workingSince) {
        this.workingSince = workingSince;
    }

    public int getSurfaceCount() {
        return surfaceCount;
    }

    public void setSurfaceCount(int surfaceCount) {
        this.surfaceCount = surfaceCount;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public boolean isConnectNow() {
        return connectNow;
    }

    public void setConnectNow(boolean connectNow) {
        this.connectNow = connectNow;
    }

    public boolean isEnableTalk() {
        return enableTalk;
    }

    public void setEnableTalk(boolean enableTalk) {
        this.enableTalk = enableTalk;
    }

    public boolean isAllowCallback() {
        return allowCallback;
    }

    public void setAllowCallback(boolean allowCallback) {
        this.allowCallback = allowCallback;
    }

    public boolean isUninstalled() {
        return uninstalled;
    }

    public void setUninstalled(boolean uninstalled) {
        this.uninstalled = uninstalled;
    }

    public String getUninstallDate() {
        return uninstallDate;
    }

    public void setUninstallDate(String uninstallDate) {
        this.uninstallDate = uninstallDate;
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

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getDoctorCategoryTitle() {
        return doctorCategoryTitle;
    }

    public void setDoctorCategoryTitle(String doctorCategoryTitle) {
        this.doctorCategoryTitle = doctorCategoryTitle;
    }
}
