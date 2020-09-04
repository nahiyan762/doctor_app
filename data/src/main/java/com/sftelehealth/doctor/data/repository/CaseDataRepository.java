package com.sftelehealth.doctor.data.repository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.data.entity.mapper.CaseEntityMapper;
import com.sftelehealth.doctor.data.repository.datastore.CaseDataStore;
import com.sftelehealth.doctor.domain.model.Case;
import com.sftelehealth.doctor.domain.repository.CasesRepository;
import io.reactivex.Observable;

/**
 * Created by Rahul on 26/12/17.
 */

public class CaseDataRepository implements CasesRepository {

    CaseDataStore caseDataStore;

    @Inject
    CaseDataRepository(CaseDataStore caseDataStore) {
        this.caseDataStore = caseDataStore;
    }

    @Override
    public Observable<List<Case>> getCases(Map<String, String> params) {
        return caseDataStore.getCases(params).map(cases -> {

            CaseEntityMapper mapper = new CaseEntityMapper();

            return mapper.transform(cases);
        });
    }

    @Override
    public Observable<Case> getCasesById(Map<String, String> params) {
        return caseDataStore.getCaseById(params).map(doctorCasesResponse -> {
            CaseEntityMapper mapper = new CaseEntityMapper();
            return mapper.transform(doctorCasesResponse.getCases().get(0));
        });
    }

    @Override
    public Observable<Boolean> initiateEmergencyCall(Map<String, String> params) {
        return caseDataStore.initiateEmergencyCall(params);
    }


}
