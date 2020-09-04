package com.sftelehealth.doctor.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rahul on 03/10/16.
 */
public class BasicResponse implements Parcelable {

    String error;

    public BasicResponse() {}

    protected BasicResponse(Parcel in) {
        error = in.readString();
    }

    public static final Creator<BasicResponse> CREATOR = new Creator<BasicResponse>() {
        @Override
        public BasicResponse createFromParcel(Parcel in) {
            return new BasicResponse(in);
        }

        @Override
        public BasicResponse[] newArray(int size) {
            return new BasicResponse[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(error);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
