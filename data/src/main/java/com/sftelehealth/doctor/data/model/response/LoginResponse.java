package com.sftelehealth.doctor.data.model.response;


import com.sftelehealth.doctor.data.model.Doctor;

/**
 * Created by Rahul on 21/06/17.
 */

public class LoginResponse {

    Doctor doctor;
    private String token;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
