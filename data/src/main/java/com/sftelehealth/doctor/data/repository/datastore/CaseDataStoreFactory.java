package com.sftelehealth.doctor.data.repository.datastore;

import android.content.Context;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.sftelehealth.doctor.data.database.mapper.DBCaseEntityMapper;
import com.sftelehealth.doctor.data.model.request.EmergencyCallRequest;
import com.sftelehealth.doctor.data.model.response.DoctorCasesResponse;
import com.sftelehealth.doctor.data.net.AuthCodeProvider;
import com.sftelehealth.doctor.data.net.CoreApi;
import com.sftelehealth.doctor.data.net.RetrofitService;
import com.sftelehealth.doctor.data.model.Case;
import io.reactivex.Observable;

/**
 * Created by Rahul on 26/12/17.
 */

public class CaseDataStoreFactory implements CaseDataStore {

    private final Context context;
    boolean isUserRegistered;
    boolean isCached = false;

    RetrofitService restService = new RetrofitService();
    CoreApi coreApi = restService.getCoreApiService();

    @Inject
    public CaseDataStoreFactory(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<Case>> getCases(Map<String, String> params) {
        return coreApi.getDoctorCases(AuthCodeProvider.getAuthCode(context), params).map(doctorCasesResponse -> {

            DBCaseEntityMapper dbMapper = new DBCaseEntityMapper();
            List<com.sftelehealth.doctor.data.database.entity.Case> tempCases = dbMapper.transform(doctorCasesResponse.getCases());

            // remove this as it is not required
            /*DatabaseObject.getInstance(context)
                    .casesDao()
                    .insertCaseList(tempCases);*/

            return  doctorCasesResponse.getCases();
        });
    }

    @Override
    public Observable<DoctorCasesResponse> getCaseById(Map<String, String> params) {

        DBCaseEntityMapper dbMapper = new DBCaseEntityMapper();

        /*return Observable.just(dbMapper)
                .subscribeOn(Schedulers.io())
                .map(dbMapperInstance -> {
                    Case tempCase = dbMapperInstance.transform(DatabaseObject
                            .getInstance(context)
                            .casesDao()
                            .getCaseFromId(Integer.parseInt(params.get("caseId"))));
                            //.getCases().get(0));
                     return tempCase;
                });*/
                //.observeOn(Schedulers.newThread());

        return coreApi.getDoctorCaseById(AuthCodeProvider.getAuthCode(context), params);
    }

    @Override
    public Observable<Boolean> initiateEmergencyCall(Map<String, String> params) {

        EmergencyCallRequest request = new EmergencyCallRequest();
        request.setCaseId(Integer.parseInt(params.get("case_id")));

        return coreApi.startDoctorEmergencyCall(AuthCodeProvider.getAuthCode(context), request).map(startDoctorCallResponse -> {
            if(startDoctorCallResponse.getStatusCode() == 1 || startDoctorCallResponse.getInfo().equalsIgnoreCase("Calling"))
                return true;
            else
                return false;
        });
    }
}
