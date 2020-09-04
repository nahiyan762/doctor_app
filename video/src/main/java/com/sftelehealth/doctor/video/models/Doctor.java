package com.sftelehealth.doctor.video.models;

public class Doctor {

    int doctorId;
    String doctorName, doctorImage;

    public Doctor(int id, String name, String image) {
        doctorId = id;
        doctorName = name;
        doctorImage = image;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }
}
