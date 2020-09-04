package com.sftelehealth.doctor.domain.repository;

import java.util.List;
import java.util.Map;
import com.sftelehealth.doctor.domain.model.Case;
import io.reactivex.Observable;

/**
 * Created by Rahul on 20/06/17.
 */

public interface CasesRepository {

    Observable<List<Case>> getCases(Map<String,String> params);

    Observable<Case> getCasesById(Map<String,String> params);

    Observable<Boolean> initiateEmergencyCall(Map<String, String> params);
}
