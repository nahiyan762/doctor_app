package com.sftelehealth.doctor.app.view.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.databinding.ObservableField;
import android.text.TextUtils;

import com.sftelehealth.doctor.domain.interactor.CreatePrescription;
import com.sftelehealth.doctor.domain.interactor.GetMedicineTypes;
import com.sftelehealth.doctor.domain.interactor.GetThisDoctor;
import com.sftelehealth.doctor.domain.model.Doctor;
import com.sftelehealth.doctor.domain.model.DoctorCategory;
import com.sftelehealth.doctor.domain.model.Medicine;
import com.sftelehealth.doctor.domain.model.MedicineType;
import com.sftelehealth.doctor.domain.model.Prescription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by Rahul on 04/01/18.
 */

public class PrescriptionActivityFragmentViewModel extends ViewModel {

    public final ObservableField<Prescription> prescription = new ObservableField<>();
    public final ObservableField<Medicine> medicine = new ObservableField<>();

    public final ObservableField<Boolean> isPrescriptionEditable = new ObservableField<>(false);

    public boolean isDietitianCategory = false;

    CreatePrescription createPrescription;
    GetMedicineTypes getMedicineTypes;
    GetThisDoctor getThisDoctor;

    public final List<MedicineType> medicineTypes = new ArrayList<>();

    public final MutableLiveData<Boolean> medicineTypesFetched = new MutableLiveData<>();
    public final MutableLiveData<Boolean> doctorDetailsFetched = new MutableLiveData<>();
    public MutableLiveData<Boolean> prescriptionCreated = new MutableLiveData<>();
    public MutableLiveData<String> notifyPrescriptionInvalidMessage = new MutableLiveData<>();

    public PrescriptionActivityFragmentViewModel(CreatePrescription createPrescription, GetMedicineTypes getMedicineTypes, GetThisDoctor getThisDoctor) {
        prescription.set(new Prescription());
        medicine.set(new Medicine());

        this.createPrescription = createPrescription;
        this.getMedicineTypes = getMedicineTypes;
        this.getThisDoctor = getThisDoctor;
    }

    public void createPrescription() {

        // remove the last empty prescription;
        // prescription.get().getMedicine().remove(prescription.get().getMedicine().size() - 1);

        // create the prescription by making a server call
        createPrescription.execute(new DisposableObserver<Prescription>() {
            @Override
            public void onNext(Prescription prescription) {}

            @Override
            public void onError(Throwable e) {
                prescriptionCreated.postValue(false);
            }

            @Override
            public void onComplete() {
                prescriptionCreated.postValue(true);
            }
        }, prescription.get());
    }

    public void getGetMedicineTypes() {

        HashMap<String, String> params = new HashMap<>();

        getMedicineTypes.execute(new DisposableObserver<List<MedicineType>>() {
            @Override
            public void onNext(List<MedicineType> medicineTypesList) {
                //if(medicineTypes.get() != null)
                medicineTypes.clear();
                medicineTypes.addAll(medicineTypesList);
                medicineTypesFetched.postValue(true);
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        }, params);
    }

    public void getDoctor(int doctorCategoryId) {
        getThisDoctor.execute(new DisposableObserver<Doctor>() {

            @Override
            public void onNext(Doctor doctorDetails) {
                for (DoctorCategory category: doctorDetails.getDoctorCategories()) {
                    if(category.getId() == doctorCategoryId && category.getCode() != null && category.getCode().equalsIgnoreCase("DIET"))
                        isDietitianCategory = true;
                }

                doctorDetailsFetched.postValue(true);
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        }, null);
    }

    public boolean validatePrescription() {
        if(TextUtils.isEmpty(prescription.get().getHistory())) {
            notifyPrescriptionInvalidMessage.postValue("History must be filled");
            return false;
        }
        if (TextUtils.isEmpty(prescription.get().getAdvice())) {
            notifyPrescriptionInvalidMessage.postValue("Advice must be filled");
            return false;
        }

        return true;
    }
}
