package com.sftelehealth.doctor.domain.model;

/**
 * Created by Rahul on 19/06/17.
 */

public class Medicine {

    private String type;
    private String name;
    private String instruction;
    private String dosageMorning;
    private String dosageAfternoon;
    private String dosageEvening;
    private String duration;
    private String dosage;

    // private boolean isDosageVisible = false;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDosageFormatted() {
        if((getDosageMorning() == null || getDosageMorning().equals("")) && (getDosageAfternoon() == null || getDosageAfternoon().equals("")) && (getDosageEvening() == null || getDosageEvening().equals("")) || (getDosageMorning().equals("0") && getDosageAfternoon().equals("0") && getDosageEvening().equals("0"))) {
            if(getDuration() == null || getDuration().equals(""))
                return "";
            else
                return duration + " days";
        }
        else
            return ((getDosageMorning() == null || getDosageMorning().equals(""))? 0 : getDosageMorning()) + "-" + ((getDosageMorning() == null || getDosageAfternoon().equals(""))? 0 : getDosageAfternoon()) + "-" + ((getDosageEvening() == null || getDosageEvening().equals(""))? 0 : getDosageEvening()) + " x " + getDuration() + " days";
    }

    public String getDosage() {
        this.dosage =  getDosageMorning() + "-" + getDosageAfternoon() + "-" + getDosageEvening();
        return this.dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDosageMorning() {
        if((dosageMorning == null || dosageMorning.equals("")) && (dosage != null && dosage.length() == 5)) {
            String[] dosageArray = dosage.split("-");
            return dosageArray[0];
        } else
            return dosageMorning;
    }

    public void setDosageMorning(String dosageMorning) {
        this.dosageMorning = dosageMorning;
    }

    public String getDosageAfternoon() {
        if((dosageAfternoon == null || dosageAfternoon.equals("")) && (dosage != null && dosage.length() == 5)) {
            String[] dosageArray = dosage.split("-");
            return dosageArray[1];
        } else
            return dosageAfternoon;
    }

    public void setDosageAfternoon(String dosageAfternoon) {
        this.dosageAfternoon = dosageAfternoon;
    }

    public String getDosageEvening() {
        if((dosageEvening == null || dosageEvening.equals("")) && (dosage != null && dosage.length() == 5)) {
            String[] dosageArray = dosage.split("-");
            return dosageArray[2];
        } else
            return dosageEvening;
    }

    public void setDosageEvening(String dosageEvening) {
        this.dosageEvening = dosageEvening;
    }

    public boolean isDosageVisible () {
        return ((getDosageMorning() == null || getDosageMorning().equals("")) && (getDosageAfternoon() == null || getDosageAfternoon().equals("") || getDosage().equals("0-0-0")) && (getDosageEvening() == null || getDosageEvening().equals("")) && (getDuration() == null || getDuration().equals("")))? false : true;
    }


}
