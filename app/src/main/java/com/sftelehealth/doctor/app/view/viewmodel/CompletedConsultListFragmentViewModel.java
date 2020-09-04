package com.sftelehealth.doctor.app.view.viewmodel;

import android.os.Build;
import android.text.TextUtils;

import com.sftelehealth.doctor.BuildConfig;
import com.sftelehealth.doctor.domain.interactor.ConfirmOtp;
import com.sftelehealth.doctor.domain.interactor.GetCaseList;
import com.sftelehealth.doctor.domain.interactor.GetThisDoctor;
import com.sftelehealth.doctor.domain.interactor.UpdateDoctor;
import com.sftelehealth.doctor.domain.model.Case;
import com.sftelehealth.doctor.domain.model.Doctor;
import com.sftelehealth.doctor.domain.model.response.UpdateDoctorResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Rahul on 20/12/17.
 */

public class CompletedConsultListFragmentViewModel extends ViewModel {

    public final ObservableField<List<Case>> caseList = new ObservableField<>();
    public List<Case> tempCaseList;

    public MutableLiveData<Boolean> notifyDataChange = new MutableLiveData<>();

    GetCaseList getCaseList;
    UpdateDoctor updateDoctor;
    GetThisDoctor getThisDoctor;
    ConfirmOtp confirmOtp;

    CompletedConsultListFragmentViewModel(GetCaseList getCaseList, UpdateDoctor updateDoctor, GetThisDoctor getThisDoctor, ConfirmOtp confirmOtp) {
        this.getCaseList = getCaseList;
        this.updateDoctor = updateDoctor;
        this.getThisDoctor = getThisDoctor;
        this.confirmOtp = confirmOtp;

        caseList.set(new ArrayList<Case>());
        tempCaseList = new ArrayList<>();
    }

    public void getCases(int page) {

        HashMap<String, String> params = new HashMap<>();
        params.put("page","" + page);
        params.put("size","20");

        getCaseList.execute(new DisposableObserver<List<Case>>() {
            @Override
            public void onNext(List<Case> cases) {

                tempCaseList.clear();
                tempCaseList.addAll(cases);

                //caseList.set(cases);
                notifyDataChange.postValue(true);
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}

        }, params);
    }

    public void updateDoctor(String androidID, String oneSignalID, float appVersion) {

        HashMap<String, String> params = new HashMap<>();
        params.put("phone_os_version", Build.VERSION.RELEASE);
        params.put("device_description", Build.MANUFACTURER + " " + Build.MODEL);
        params.put("android_id", androidID);
        params.put("onesignal_id", oneSignalID);
        params.put("app_version", "" + appVersion);

        updateDoctor.execute(new DisposableObserver<UpdateDoctorResponse>() {
            @Override
            public void onNext(UpdateDoctorResponse updateDoctorResponse) {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        }, params);
    }

    public void updateDoctorDetails() {

        getThisDoctor.execute(new DisposableObserver<Doctor>() {

            @Override
            public void onNext(Doctor doctorDetails) {
                // Update doctor object to every time the app is opened
                loginUser(doctorDetails.getPhone());
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        }, null);
    }

    public void loginUser(String phoneNumber) {

        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phoneNumber);
        params.put("otp", "654646");
        params.put("version_name", BuildConfig.VERSION_NAME);

        confirmOtp.execute(new DisposableObserver<Doctor>() {
            @Override
            public void onNext(Doctor doctor) {
                if(TextUtils.isEmpty(doctor.getImage()) || TextUtils.isEmpty(doctor.getSignatureImage())) {
                    // must go to Profile Setup screen
                    // navigateToMainView.postValue(false);
                } else {
                    // must be redirected to Main View
                    // navigateToMainView.postValue(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                // check the error and show appropriate messages
            }

            @Override
            public void onComplete() {

            }
        }, params);
    }

}
