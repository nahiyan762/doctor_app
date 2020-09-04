package com.sftelehealth.doctor.domain.model.response;

/**
 * Created by Rahul on 16/04/18.
 */

public class IsUserAuthenticatedResponse {

    boolean isDoctorObjectAvailable;
    boolean isTokenAvailable;
    boolean isDoctorDataSet;
    boolean hasMigratedToDoctorCategories;
    String phoneNumber;

    public boolean isDoctorObjectAvailable() {
        return isDoctorObjectAvailable;
    }

    public void setDoctorObjectAvailable(boolean doctorObjectAvailable) {
        isDoctorObjectAvailable = doctorObjectAvailable;
    }

    public boolean isTokenAvailable() {
        return isTokenAvailable;
    }

    public void setTokenAvailable(boolean tokenAvailable) {
        isTokenAvailable = tokenAvailable;
    }

    public boolean isDoctorDataSet() {
        return isDoctorDataSet;
    }

    public void setDoctorDataSet(boolean doctorDataSet) {
        isDoctorDataSet = doctorDataSet;
    }

    public boolean isHasMigratedToDoctorCategories() {
        return hasMigratedToDoctorCategories;
    }

    public void setHasMigratedToDoctorCategories(boolean hasMigratedToDoctorCategories) {
        this.hasMigratedToDoctorCategories = hasMigratedToDoctorCategories;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
