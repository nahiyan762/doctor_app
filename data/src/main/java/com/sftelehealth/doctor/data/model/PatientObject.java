package com.sftelehealth.doctor.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 19/06/17.
 */

public class PatientObject {

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("AddedOn")
    @Expose
    private String addedOn;
    @SerializedName("MedicalConditions")
    @Expose
    private List<Object> medicalConditions = new ArrayList<Object>();
    @SerializedName("Weight")
    @Expose
    private int weight;
    @SerializedName("Relationship")
    @Expose
    private String relationship;
    @SerializedName("BirthDate")
    @Expose
    private String birthDate;
    @SerializedName("Height")
    @Expose
    private int height;
    @SerializedName("patientimage")
    @Expose
    private String patientimage;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("BloodGroup")
    @Expose
    private String bloodGroup;
    @SerializedName("MedicalProcedures")
    @Expose
    private List<Object> medicalProcedures = new ArrayList<Object>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public List<Object> getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(List<Object> medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPatientimage() {
        return patientimage;
    }

    public void setPatientimage(String patientimage) {
        this.patientimage = patientimage;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public List<Object> getMedicalProcedures() {
        return medicalProcedures;
    }

    public void setMedicalProcedures(List<Object> medicalProcedures) {
        this.medicalProcedures = medicalProcedures;
    }
}
