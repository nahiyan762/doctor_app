package com.sftelehealth.doctor.app.view.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import com.sftelehealth.doctor.domain.interactor.UpdateSignature;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Rahul on 25/01/18.
 */

public class SignatureActivityViewModel extends ViewModel {

    public int doctorId;
    UpdateSignature updateSignature;

    public final MutableLiveData<Boolean> doctorSignatureUpdated = new MutableLiveData<>();

    public SignatureActivityViewModel(UpdateSignature updateSignature) {
        this.updateSignature = updateSignature;
    }

    public void updateSignature(String path) {

        HashMap<String, String> params = new HashMap<>();
        params.put("doctor_id", String.valueOf(doctorId));
        params.put("file_path", path);
        updateSignature.execute(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                doctorSignatureUpdated.postValue(true);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, params);
    }
}
