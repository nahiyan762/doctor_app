package com.sftelehealth.doctor.domain.model.response;

/**
 * Created by Rahul on 19/06/17.
 */

public class ShouldUpdateResponse {

    boolean shouldUpdate;
    boolean mustUpdate;
    String phone;
    int freeFollowUpTime;
    int appointmentDaysLimit;
    int videoAcceptWaitTime;
    int dialerButtonVisibleTime;
    String countryISDCode;
    String countryCode;

    public boolean isShouldUpdate() {
        return shouldUpdate;
    }

    public void setShouldUpdate(boolean shouldUpdate) {
        this.shouldUpdate = shouldUpdate;
    }

    public boolean isMustUpdate() {
        return mustUpdate;
    }

    public void setMustUpdate(boolean mustUpdate) {
        this.mustUpdate = mustUpdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getFreeFollowUpTime() {
        return freeFollowUpTime;
    }

    public int getAppointmentDaysLimit() {
        return appointmentDaysLimit;
    }

    public int getVideoAcceptWaitTime() {
        return videoAcceptWaitTime;
    }

    public int getDialerButtonVisibleTime() {
        return dialerButtonVisibleTime;
    }

    public String getCountryISDCode() {
        return countryISDCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setFreeFollowUpTime(int freeFollowUpTime) {
        this.freeFollowUpTime = freeFollowUpTime;
    }

    public void setAppointmentDaysLimit(int appointmentDaysLimit) {
        this.appointmentDaysLimit = appointmentDaysLimit;
    }

    public void setVideoAcceptWaitTime(int videoAcceptWaitTime) {
        this.videoAcceptWaitTime = videoAcceptWaitTime;
    }

    public void setDialerButtonVisibleTime(int dialerButtonVisibleTime) {
        this.dialerButtonVisibleTime = dialerButtonVisibleTime;
    }

    public void setCountryISDCode(String countryISDCode) {
        this.countryISDCode = countryISDCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}