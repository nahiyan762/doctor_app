package com.sftelehealth.doctor.data.model.response;

/**
 * Created by Rahul on 19/06/17.
 */

public class StartDoctorCallResponse {

    private String info;
    private Integer statusCode;
    private String caseId;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
