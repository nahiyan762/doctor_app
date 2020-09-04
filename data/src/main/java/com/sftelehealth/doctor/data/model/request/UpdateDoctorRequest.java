package com.sftelehealth.doctor.data.model.request;

/**
 * Created by Rahul on 19/06/17.
 */

public class UpdateDoctorRequest {

    String oneSignalId;
    String phoneOsVersion;
    String deviceDescription;
    String androidId;
    String phoneOs = "Android";
    String appVersion;

    public String getOneSignalId() {
        return oneSignalId;
    }

    public void setOneSignalId(String oneSignalId) {
        this.oneSignalId = oneSignalId;
    }

    public String getPhoneOsVersion() {
        return phoneOsVersion;
    }

    public void setPhoneOsVersion(String phoneOsVersion) {
        this.phoneOsVersion = phoneOsVersion;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
