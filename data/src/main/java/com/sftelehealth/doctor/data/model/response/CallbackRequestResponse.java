package com.sftelehealth.doctor.data.model.response;

import java.util.ArrayList;
import java.util.List;

import com.sftelehealth.doctor.data.model.CallbackRequest;

/**
 * Created by Rahul on 29/12/17.
 */

public class CallbackRequestResponse {

    private List<CallbackRequest> requests = new ArrayList<CallbackRequest>();

    public List<CallbackRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<CallbackRequest> requests) {
        this.requests = requests;
    }
}
