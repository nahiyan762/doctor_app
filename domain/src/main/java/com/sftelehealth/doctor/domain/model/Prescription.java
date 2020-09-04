package com.sftelehealth.doctor.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.sftelehealth.doctor.domain.helper.DateTimeComputationHelper;
import com.sftelehealth.doctor.domain.helper.DateTimeHelper;

/**
 * Created by Rahul on 19/06/17.
 */

public class Prescription {

    private Integer id;
    private String advice;
    private Integer consultId;
    private String history;
    private List<Medicine> medicine = new ArrayList<>();
    private List<DietChart> dietChart = new ArrayList<>();
    private String updatedAt;
    private String createdAt;
    private boolean active;
    private boolean isDietChart;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public List<DietChart> getDietChart() {
        return dietChart;
    }

    public void setDietChart(List<DietChart> dietChart) {
        this.dietChart = dietChart;
    }

    public boolean isDietChart() {
        return isDietChart;
    }

    public void setDietChart(boolean dietChart) {
        isDietChart = dietChart;
    }

    public String getPrescriptionDateTime() {
        DateTimeComputationHelper dtch = new DateTimeComputationHelper();
        return DateTimeHelper.toLocaleDayTimeYear(dtch.convertGMTToIST(DateTimeHelper.parseDateTime(createdAt)));
    }
}
