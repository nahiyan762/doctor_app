package com.sftelehealth.doctor.domain.repository;

import java.util.HashMap;

import com.sftelehealth.doctor.domain.model.Doctor;
import io.reactivex.Observable;

/**
 * Created by Rahul on 20/06/17.
 */

public interface DoctorRepository {

    Observable<String> doctorSignatureUpdate (HashMap<String, String> params);

    Observable<String> doctorImageUpdate (HashMap<String, String> params);

    Observable<Doctor> getDoctor();
}
