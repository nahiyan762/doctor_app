package com.sftelehealth.doctor.app.view.viewmodel;

import com.sftelehealth.doctor.domain.interactor.GetCaseById;
import com.sftelehealth.doctor.domain.interactor.GetPrescriptionAndDocumentsForCase;
import com.sftelehealth.doctor.domain.interactor.GetSystemInfo;
import com.sftelehealth.doctor.domain.interactor.GetThisDoctor;
import com.sftelehealth.doctor.domain.interactor.InitiateCall;
import com.sftelehealth.doctor.domain.interactor.InitiateEmergencyCall;
import com.sftelehealth.doctor.domain.model.Case;
import com.sftelehealth.doctor.domain.model.CasePrescriptionsAndDocuments;
import com.sftelehealth.doctor.domain.model.Doctor;
import com.sftelehealth.doctor.domain.model.SystemInfo;

import java.util.HashMap;
import java.util.Map;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.functions.Function3;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Rahul on 29/12/17.
 */

public class CaseDetailsActivityFragmentViewModel extends ViewModel {


    public final ObservableField<Case> caseObject = new ObservableField<>();
    public final ObservableField<String> caseId = new ObservableField<>();

    public final ObservableField<Doctor> doctor = new ObservableField<>();

    // public final ObservableField<CasePrescriptionsAndDocuments> prescriptionAndDocuments = new ObservableField<>();

    public final MutableLiveData<Boolean> notifyCaseFetched = new MutableLiveData<>();
    public final MutableLiveData<Boolean> notifyEmergencyCallInitiated = new MutableLiveData<>();

    public boolean hasCallStarted = false;

    public boolean startCall = false;
    public String countryCode = "";

    GetCaseById getCaseById;
    GetPrescriptionAndDocumentsForCase getPrescriptionAndDocumentsForCase;
    GetThisDoctor getThisDoctor;
    InitiateEmergencyCall initiateEmergencyCall;
    InitiateCall initiateCallback;
    GetSystemInfo getSystemInfo;

    CaseDetailsActivityFragmentViewModel(GetCaseById getCaseById,
                                         GetPrescriptionAndDocumentsForCase getPrescriptionAndDocumentsForCase,
                                         InitiateEmergencyCall initiateEmergencyCall,
                                         InitiateCall initiateCallback,
                                         GetThisDoctor getThisDoctor,
                                         GetSystemInfo getSystemInfo) {

        this.getCaseById = getCaseById;
        this.getPrescriptionAndDocumentsForCase = getPrescriptionAndDocumentsForCase;
        this.initiateEmergencyCall = initiateEmergencyCall;
        this.initiateCallback = initiateCallback;
        this.getThisDoctor = getThisDoctor;
        this.getSystemInfo = getSystemInfo;
    }

    public void getDataForScreen() {

        HashMap<String, String> params = new HashMap<>();
        params.put("caseId", caseId.get());

        Observable.combineLatest(getCaseById.getObservable(params),
                getPrescriptionAndDocumentsForCase.getObservable(params),
                getThisDoctor.getObservable(null),
                new Function3<Case, CasePrescriptionsAndDocuments, Doctor, Boolean>() {

                    @Override
                    public Boolean apply(Case aCase, CasePrescriptionsAndDocuments casePrescriptionsAndDocuments, Doctor doctorObject) throws Exception {
                        caseObject.set(aCase);
                        doctor.set(doctorObject);
                        caseObject.get().setPrescriptionAndDocuments(casePrescriptionsAndDocuments);
                        //prescriptionAndDocuments.set(casePrescriptionsAndDocuments);
                        // unset the flag to check whether the
                        /*if(!caseObject.get().isConsultPending())
                            hasCallStarted = false;*/

                        return true;
                    }
                })
        .subscribeOn(Schedulers.from(getCaseById.getThreadExecutor()))
        .observeOn(getCaseById.getPostExecutionThread().getScheduler())
        .subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean fetchedSuccessfully) {
            }

            @Override
            public void onError(Throwable e) {
                notifyCaseFetched.postValue(false);
            }

            @Override
            public void onComplete() {
                notifyCaseFetched.postValue(true);
            }
        });
    }

    public void getSystemInfo() {

        HashMap<String, String> params = new HashMap<>();

        getSystemInfo.execute(new DisposableObserver<SystemInfo>() {
            @Override
            public void onNext(SystemInfo systemInfo) {
                if(!systemInfo.getCountryCode().equalsIgnoreCase("IN")) {
                    countryCode = systemInfo.getCountryCode() + "_";
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, params);
    }

    /*private void getCaseById() {

        HashMap<String, String> params = new HashMap<>();
        params.put("caseId", caseId.get());

        getCaseById.execute(new DisposableObserver<Case>() {
            @Override
            public void onNext(Case caseItem) {
                caseObject.set(caseItem);
                notifyCaseFetched.postValue(true);
            }

            @Override
            public void onError(Throwable e) {
                notifyCaseFetched.postValue(false);
            }

            @Override
            public void onComplete() {
                getDoctorDetails();
            }
        }, params);
    }

    private void getPrescriptionsAndDocuments() {

        HashMap<String, String> params = new HashMap<>();
        params.put("caseId", caseId.get());

        getPrescriptionAndDocumentsForCase.execute(new DisposableObserver<CasePrescriptionsAndDocuments>() {

            @Override
            public void onNext(CasePrescriptionsAndDocuments casePrescriptionsAndDocuments) {

                caseObject.get().setPrescriptionAndDocuments(casePrescriptionsAndDocuments);
                notifyCaseFetched.postValue(true);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, params);
    }*/

    public void initiateEmergencyCall() {

        Map<String, String> params = new HashMap<>();
        params.put("case_id", String.valueOf(caseId.get()));

        initiateEmergencyCall.execute(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                notifyEmergencyCallInitiated.postValue(true);
            }
        }, params);
    }

    public void initiateCall() {

        HashMap<String, String> params = new HashMap<>();
        params.put("callback_id", String.valueOf(caseObject.get().getCallbackId()));

        // start knowlarity call
        initiateCallback.execute(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean callStarted) {
                Timber.d("initiateCall onNext()");
            }

            @Override
            public void onError(Throwable e) {
                Timber.d("initiateCall onError()");
            }

            @Override
            public void onComplete() {
                Timber.d("initiateCall onComplete()");
                // hasCallStarted = true;
                // getCallbackRequest();
                // notify change of data
                // notifyCallStarted.postValue(true);
            }
        }, params);
    }

}
