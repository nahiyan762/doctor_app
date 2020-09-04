package com.sftelehealth.doctor.video.agora.call;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import static com.sftelehealth.doctor.video.Constant.COUNTRY_CODE;

public class CallData implements Parcelable {

    int doctorId;
    String doctorName;
    String doctorImage;
    String doctorPhone;

    String patientUserId;
    String patientName;
    String userAccount;

    String channelName;
    State state;
    int uid;

    // SharedPreferences sp;
    // String countryCode = "";

    public CallData() {}

    protected CallData(Parcel in) {
        doctorId = in.readInt();
        doctorName = in.readString();
        doctorImage = in.readString();
        doctorPhone = in.readString();
        patientUserId = in.readString();
        patientName = in.readString();
        userAccount = in.readString();
        channelName = in.readString();
        state = in.readParcelable(State.class.getClassLoader());
        uid = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(doctorId);
        dest.writeString(doctorName);
        dest.writeString(doctorImage);
        dest.writeString(doctorPhone);
        dest.writeString(patientUserId);
        dest.writeString(patientName);
        dest.writeString(userAccount);
        dest.writeString(channelName);
        dest.writeParcelable(state, flags);
        dest.writeInt(uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CallData> CREATOR = new Creator<CallData>() {
        @Override
        public CallData createFromParcel(Parcel in) {
            return new CallData(in);
        }

        @Override
        public CallData[] newArray(int size) {
            return new CallData[size];
        }
    };

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

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getDoctorAccount(SharedPreferences sp) {
        // this.sp = sp;

        String countryCode = sp.getString(COUNTRY_CODE, "") + "_";
        return countryCode + "doctor_" + doctorId;
    }

    public String getPatientUserId() {
        return patientUserId;
    }

    public void setPatientUserId(String patientUserId) {
        this.patientUserId = patientUserId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getUserAccount(SharedPreferences sp) {
        String countryCode = sp.getString(COUNTRY_CODE, "") + "_";
        return countryCode + "user_" + patientUserId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
