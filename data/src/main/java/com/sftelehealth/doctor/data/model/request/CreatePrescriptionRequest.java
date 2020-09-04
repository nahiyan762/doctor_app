package com.sftelehealth.doctor.data.model.request;

import java.util.List;

import com.sftelehealth.doctor.data.model.Medicine;

/**
 * Created by Rahul on 19/06/17.
 */

public class CreatePrescriptionRequest {

    private String history;
    private String advice;
    private Integer consultId;
    private List<Medicine> medicine = null;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Integer getConsultId() {
        return consultId;
    }

    public void setConsultId(Integer consultId) {
        this.consultId = consultId;
    }

    public List<Medicine> getMedicine() {
        return medicine;
    }

    public void setMedicine(List<Medicine> medicine) {
        this.medicine = medicine;
    }
}
