package com.sftelehealth.doctor.data.repository.datastore;

import java.util.HashMap;

import com.sftelehealth.doctor.data.model.Doctor;
import io.reactivex.Observable;

/**
 * Created by Rahul on 16/01/18.
 */

public interface DoctorDataStore {

    Observable<String> doctorSignatureUpdate (HashMap<String, String> params);

    Observable<String> doctorImageUpdate (HashMap<String, String> params);

    Observable<Doctor> getDoctor();
}
