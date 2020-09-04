package com.sftelehealth.doctor.app.listener;

/**
 * Created by Rahul on 04/01/18.
 */

public interface PrescriptionEventListener {

    void addMedicine();
    void removeMedicine(String medicineName);
    void previewPrescription();
}
