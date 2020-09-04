package com.sftelehealth.doctor.video.agora.call;

import android.os.Parcel;
import android.os.Parcelable;

import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_STATE_ENDED;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_STATE_INITIATED;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_STATE_INVITE_RECEIVED_BY_USER;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_STATE_ON_GOING;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_STATE_PAUSED;

public class State implements Parcelable {

    int videoCallState = -1;
    int phoneState = -1;
    boolean isRemotePaused = false;
    boolean isVideoMuted = false;

    public State() {}

    protected State(Parcel in) {
        videoCallState = in.readInt();
        phoneState = in.readInt();
        isRemotePaused = in.readByte() != 0;
        isVideoMuted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(videoCallState);
        dest.writeInt(phoneState);
        dest.writeByte((byte) (isRemotePaused ? 1 : 0));
        dest.writeByte((byte) (isVideoMuted ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<State> CREATOR = new Creator<State>() {
        @Override
        public State createFromParcel(Parcel in) {
            return new State(in);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
        }
    };

    public void setVideoCallState(int videoCallState) {
        this.videoCallState = videoCallState;
    }

    public int getPhoneState() {
        return phoneState;
    }

    public void setPhoneState(int phoneState) {
        this.phoneState = phoneState;
    }

    public int getVideoCallState() {
        return videoCallState;
    }

    public boolean isRemotePaused() {
        return isRemotePaused;
    }

    public void setRemotePaused(boolean remotePaused) {
        isRemotePaused = remotePaused;
    }

    public boolean isVideoMuted() {
        return isVideoMuted;
    }

    public void setVideoMuted(boolean videoMuted) {
        isVideoMuted = videoMuted;
    }

    public boolean isVideoCallOnGoing() {
        switch (videoCallState) {
            case VIDEO_CALL_STATE_INITIATED : return false;
            case VIDEO_CALL_STATE_INVITE_RECEIVED_BY_USER : return true;
            case VIDEO_CALL_STATE_ON_GOING : return true;
            case VIDEO_CALL_STATE_PAUSED : return true;
            case VIDEO_CALL_STATE_ENDED: return false;
            default: return false;
        }
    }
}
