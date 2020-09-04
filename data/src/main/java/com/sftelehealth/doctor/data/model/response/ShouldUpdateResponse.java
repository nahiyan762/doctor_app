package com.sftelehealth.doctor.data.model.response;

/**
 * Created by Rahul on 19/06/17.
 */

public class ShouldUpdateResponse {

    boolean shouldUpdate;
    boolean mustUpdate;
    String phone;
    String whatsAppPhone;
    int freeFollowUpTime;
    int appointmentDaysLimit;
    int videoAcceptWaitTime;
    int dialerButtonVisibleTime;
    String countryISDCode;
    String countryCode;
    String timezone;
    String cliNumbers;

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

    public void setFreeFollowUpTime(int freeFollowUpTime) {
        this.freeFollowUpTime = freeFollowUpTime;
    }

    public int getAppointmentDaysLimit() {
        return appointmentDaysLimit;
    }

    public void setAppointmentDaysLimit(int appointmentDaysLimit) {
        this.appointmentDaysLimit = appointmentDaysLimit;
    }

    public int getVideoAcceptWaitTime() {
        return videoAcceptWaitTime;
    }

    public void setVideoAcceptWaitTime(int videoAcceptWaitTime) {
        this.videoAcceptWaitTime = videoAcceptWaitTime;
    }

    public int getDialerButtonVisibleTime() {
        return dialerButtonVisibleTime;
    }

    public void setDialerButtonVisibleTime(int dialerButtonVisibleTime) {
        this.dialerButtonVisibleTime = dialerButtonVisibleTime;
    }

    public String getCountryISDCode() {
        return countryISDCode;
    }

    public void setCountryISDCode(String countryISDCode) {
        this.countryISDCode = countryISDCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getWhatsAppPhone() {
        return whatsAppPhone;
    }

    public void setWhatsAppPhone(String whatsAppPhone) {
        this.whatsAppPhone = whatsAppPhone;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCliNumbers() {
        return cliNumbers;
    }

    public void setCliNumbers(String cliNumbers) {
        this.cliNumbers = cliNumbers;
    }
}
