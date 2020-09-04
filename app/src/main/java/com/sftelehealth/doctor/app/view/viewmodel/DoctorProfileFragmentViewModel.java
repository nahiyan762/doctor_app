package com.sftelehealth.doctor.app.view.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.databinding.ObservableField;
import android.text.TextUtils;

import java.util.HashMap;

import javax.inject.Inject;

import com.sftelehealth.doctor.domain.interactor.GetThisDoctor;
import com.sftelehealth.doctor.domain.interactor.UpdateDoctorImage;
import com.sftelehealth.doctor.domain.model.Doctor;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Rahul on 15/01/18.
 */

public class DoctorProfileFragmentViewModel extends ViewModel {

    public MutableLiveData<Boolean> doctorDetailsFetched = new MutableLiveData<>();

    public final ObservableField<Doctor> doctor = new ObservableField<>();

    public String validationMessage = "";
    public boolean validateModel;
    public MutableLiveData<Boolean> isModelValidated = new MutableLiveData<>();

    GetThisDoctor getThisDoctor;
    UpdateDoctorImage updateDoctorImage;

    @Inject
    DoctorProfileFragmentViewModel(GetThisDoctor getThisDoctor, UpdateDoctorImage updateDoctorImage) {
        this.getThisDoctor = getThisDoctor;
        this.updateDoctorImage = updateDoctorImage;
    }

    public void getDoctorDetails() {
        getThisDoctor.execute(new DisposableObserver<Doctor>() {

            @Override
            public void onNext(Doctor doctorDetails) {
                doctor.set(doctorDetails);
                doctorDetailsFetched.postValue(true);
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        }, null);
    }

    public void validateProfile() {

        String message = "Please upload ";
        boolean isModelValid = true;
        if(TextUtils.isEmpty(doctor.get().getImage())) {

            message = "Profile image";
            isModelValid = false;
        }

        if(TextUtils.isEmpty(doctor.get().getSignatureImage())) {
            // set a field specifying that its empty
            if(!message.equalsIgnoreCase("Please upload "))
                message = message + " and ";

            message = message + "Digital Signature";
            isModelValid = false;
        }

        // set boolean flag for validation
        // set appropriate message
        // notify view
        validationMessage = message;
        isModelValidated.postValue(isModelValid);

    }

    public void updateDoctorImage(String filePath) {

        HashMap<String, String> params = new HashMap<>();
        params.put("doctor_id", String.valueOf(doctor.get().getId()));
        params.put("file_path", filePath);

        updateDoctorImage.execute(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                getDoctorDetails();
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        }, params);
    }

}