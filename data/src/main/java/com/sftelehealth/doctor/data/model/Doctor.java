package com.sftelehealth.doctor.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by rahul on 10/10/16.
 */
public class Doctor implements Parcelable {

    int id, doctorCategoryId, workingSince, rate, followUpRate, callbackRate, cost;
    String title, firstName, lastName, regNo, signatureImage, qualification, email, isdCode, phone, hospital, gender, location, languages, doctorCategoryTitle;
    boolean isDisabled, connectNow, allowCallback;
    // String doctorCategoryCode;
    ArrayList<DoctorCategory> doctorCategories;

    String name = "", image, experience;

    String token;


    protected Doctor(Parcel in) {
        id = in.readInt();
        doctorCategoryId = in.readInt();
        workingSince = in.readInt();
        rate = in.readInt();
        followUpRate = in.readInt();
        callbackRate = in.readInt();
        cost = in.readInt();
        title = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        regNo = in.readString();
        signatureImage = in.readString();
        qualification = in.readString();
        email = in.readString();
        isdCode = in.readString();
        phone = in.readString();
        hospital = in.readString();
        gender = in.readString();
        location = in.readString();
        languages = in.readString();
        doctorCategoryTitle = in.readString();
        isDisabled = in.readByte() != 0;
        connectNow = in.readByte() != 0;
        allowCallback = in.readByte() != 0;
        name = in.readString();
        image = in.readString();
        experience = in.readString();
        token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(doctorCategoryId);
        dest.writeInt(workingSince);
        dest.writeInt(rate);
        dest.writeInt(followUpRate);
        dest.writeInt(callbackRate);
        dest.writeInt(cost);
        dest.writeString(title);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(regNo);
        dest.writeString(signatureImage);
        dest.writeString(qualification);
        dest.writeString(email);
        dest.writeString(isdCode);
        dest.writeString(phone);
        dest.writeString(hospital);
        dest.writeString(gender);
        dest.writeString(location);
        dest.writeString(languages);
        dest.writeString(doctorCategoryTitle);
        dest.writeByte((byte) (isDisabled ? 1 : 0));
        dest.writeByte((byte) (connectNow ? 1 : 0));
        dest.writeByte((byte) (allowCallback ? 1 : 0));
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(experience);
        dest.writeString(token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

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

    public boolean isAllowCallback() {
        return allowCallback;
    }

    public void setAllowCallback(boolean allowCallback) {
        this.allowCallback = allowCallback;
    }

    /**
     * No setter must be created for this field as this is a helper method to get name from
     * {firstName} and {lastName}
     * @return String
     */
    public String getName() {

        if(getFirstName() == null && getLastName() == null)
            return "";
        else if(getFirstName() == null)
            return (getTitle() != null && !getTitle().equals(""))? getTitle() + " " + getLastName() : getLastName();
        else if(getLastName() == null)
            return (getTitle() != null && !getTitle().equals(""))? getTitle() + " " + getFirstName() : getFirstName();
        else
            return (getTitle() != null && !getTitle().equals(""))? getTitle() + " " + getFirstName() + " " + getLastName() : getFirstName() + " " + getLastName();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getCost() {
        return cost;
    }

    /*public void setCost(int cost) {
        this.cost = cost;
    }

    public ArrayList<String> getQualifications() {
        return qualifications;
    }

    public void setQualifications(ArrayList<String> qualifications) {
        this.qualifications = qualifications;
    }

    public ArrayList<String> getHospitals() {
        return hospitals;
    }

    public void setHospitals(ArrayList<String> hospitals) {
        this.hospitals = hospitals;
    }

    public String getAllHospitals() {
        allHospitals = "";
        if(getHospitals().iterator() != null) {

            for (String tempHospital: getHospitals())
                allHospitals = allHospitals + tempHospital + ", ";

            // remove the comma and space from the end of the string
            if(!allHospitals.equals(""))
                allHospitals = allHospitals.substring(0, allHospitals.length()-2);
        }

        return allHospitals;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getFollowUpRate() {
        return followUpRate;
    }

    public void setFollowUpRate(int followUpRate) {
        this.followUpRate = followUpRate;
    }

    public int getCallbackRate() {
        return callbackRate;
    }

    public void setCallbackRate(int callbackRate) {
        this.callbackRate = callbackRate;
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

    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDoctorCategoryTitle() {
        return doctorCategoryTitle;
    }

    public void setDoctorCategoryTitle(String doctorCategoryTitle) {
        this.doctorCategoryTitle = doctorCategoryTitle;
    }

    /*public String getDoctorCategoryCode() {
        return doctorCategoryCode;
    }

    public void setDoctorCategoryCode(String doctorCategoryCode) {
        this.doctorCategoryCode = doctorCategoryCode;
    }*/
}