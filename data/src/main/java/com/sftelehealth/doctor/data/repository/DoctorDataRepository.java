package com.sftelehealth.doctor.data.repository;


import java.util.HashMap;

import javax.inject.Inject;

import com.sftelehealth.doctor.data.entity.mapper.DoctorEntityMapper;
import com.sftelehealth.doctor.data.repository.datastore.DoctorDataStore;
import com.sftelehealth.doctor.domain.model.Doctor;
import com.sftelehealth.doctor.domain.repository.DoctorRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 16/01/18.
 */

public class DoctorDataRepository implements DoctorRepository {

    DoctorDataStore doctorDataStore;

    @Inject
    DoctorDataRepository(DoctorDataStore doctorDataStore) {
        this.doctorDataStore = doctorDataStore;
    }

    @Override
    public Observable<String> doctorSignatureUpdate(HashMap<String, String> params) {
        DoctorEntityMapper mapper = new DoctorEntityMapper();
        return doctorDataStore.doctorSignatureUpdate(params);
    }

    @Override
    public Observable<String> doctorImageUpdate(HashMap<String, String> params) {
        DoctorEntityMapper mapper = new DoctorEntityMapper();
        return doctorDataStore.doctorImageUpdate(params);   //.map(doctor -> mapper.transformToDomainEntity(doctor));
    }

    @Override
    public Observable<Doctor> getDoctor() {
        DoctorEntityMapper mapper = new DoctorEntityMapper();
        return doctorDataStore.getDoctor().map(doctor -> mapper.transformToDomainEntity(doctor));
    }
}
