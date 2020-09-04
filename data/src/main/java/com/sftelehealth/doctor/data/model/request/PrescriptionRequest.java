package com.sftelehealth.doctor.data.model.request;

import java.util.ArrayList;
import java.util.List;

import com.sftelehealth.doctor.data.model.Medicine;

/**
 * Created by Rahul on 23/01/18.
 */

public class PrescriptionRequest {

    //private Integer id;
    private String advice;
    private Integer consultId;
    private String history;
    private List<Medicine> medicine = new ArrayList<>();

    /*public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }*/

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

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public List<Medicine> getMedicine() {
        return medicine;
    }

    public void setMedicine(List<Medicine> medicine) {
        this.medicine = medicine;
    }
}
