package com.sftelehealth.doctor.app.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import com.sftelehealth.doctor.data.repository.CallbackRequestDataRepository;
import com.sftelehealth.doctor.data.repository.CaseDataRepository;
import com.sftelehealth.doctor.data.repository.DoctorDataRepository;
import com.sftelehealth.doctor.data.repository.PrescriptionDataRepository;
import com.sftelehealth.doctor.data.repository.SystemDataRepository;
import com.sftelehealth.doctor.data.repository.datastore.CallbackRequestDataStore;
import com.sftelehealth.doctor.data.repository.datastore.CallbackRequestDataStoreFactory;
import com.sftelehealth.doctor.data.repository.datastore.CaseDataStore;
import com.sftelehealth.doctor.data.repository.datastore.CaseDataStoreFactory;
import com.sftelehealth.doctor.data.repository.datastore.DoctorDataStore;
import com.sftelehealth.doctor.data.repository.datastore.DoctorDataStoreFactory;
import com.sftelehealth.doctor.data.repository.datastore.PrescriptionDataStore;
import com.sftelehealth.doctor.data.repository.datastore.PrescriptionDataStoreFactory;
import com.sftelehealth.doctor.data.repository.datastore.SystemDataStore;
import com.sftelehealth.doctor.data.repository.datastore.SystemDataStoreFactory;
import com.sftelehealth.doctor.domain.repository.CallbackRequestRepository;
import com.sftelehealth.doctor.domain.repository.CasesRepository;
import com.sftelehealth.doctor.domain.repository.DoctorRepository;
import com.sftelehealth.doctor.domain.repository.PrescriptionRepository;
import com.sftelehealth.doctor.domain.repository.SystemRepository;

/**
 * Created by Rahul on 04/10/17.
 */
@Module
public class UseCaseModule {

    public UseCaseModule() {}

    @Provides
    SystemRepository providesSystemRepository(SystemDataRepository systemDataRepository) {
        return systemDataRepository;
    }

    @Provides
    SystemDataStore providesSystemDataRepository(SystemDataStoreFactory systemDataStoreFactory) {
        return systemDataStoreFactory;
    }

    @Provides
    CasesRepository providesCaseRepository(CaseDataRepository caseDataRepository) {
        return caseDataRepository;
    }

    @Provides
    CaseDataStore providesCaseDataRepository(CaseDataStoreFactory caseDataStoreFactory) {
        return caseDataStoreFactory;
    }

    @Provides
    CallbackRequestRepository providesCallbackRequest(CallbackRequestDataRepository callbackRequestDataRepository) {
        return callbackRequestDataRepository;
    }

    @Provides
    CallbackRequestDataStore providesCallbackRequestDataRepository(CallbackRequestDataStoreFactory callbackRequestDataStoreFactory) {
        return callbackRequestDataStoreFactory;
    }

    @Provides
    PrescriptionRepository providesPrescriptionRepository(PrescriptionDataRepository prescriptionDataRepository) {
        return prescriptionDataRepository;
    }

    @Provides
    PrescriptionDataStore providesPrescriptionDataRepository(PrescriptionDataStoreFactory prescriptionDataStoreFactory) {
        return prescriptionDataStoreFactory;
    }

    @Provides
    DoctorDataStore providesDoctorDataStore(DoctorDataStoreFactory doctorDataStoreFactory) {
        return doctorDataStoreFactory;
    }

    @Provides
    DoctorRepository providesDoctorRepository(DoctorDataRepository doctorDataRepository) {
        return doctorDataRepository;
    }

}
