package com.sftelehealth.doctor.data.repository.datastore;

import java.util.List;
import java.util.Map;

import com.sftelehealth.doctor.data.model.Case;
import com.sftelehealth.doctor.data.model.response.DoctorCasesResponse;
import io.reactivex.Observable;

/**
 * Created by Rahul on 26/12/17.
 */

public interface CaseDataStore {

    Observable<List<Case>> getCases(Map<String, String> params);

    Observable<DoctorCasesResponse> getCaseById(Map<String, String> params);

    Observable<Boolean> initiateEmergencyCall(Map<String, String> params);
}
