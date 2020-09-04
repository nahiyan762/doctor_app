/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sftelehealth.doctor.app.view.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sftelehealth.doctor.app.internal.di.components.UseCaseComponent;
import com.sftelehealth.doctor.data.repository.datasource.CallbackRequestFactory;
import com.sftelehealth.doctor.domain.interactor.AcceptRejectCallback;
import com.sftelehealth.doctor.domain.interactor.ConfirmOtp;
import com.sftelehealth.doctor.domain.interactor.CreatePrescription;
import com.sftelehealth.doctor.domain.interactor.GenerateOTP;
import com.sftelehealth.doctor.domain.interactor.GetAppointmentsList;
import com.sftelehealth.doctor.domain.interactor.GetCallbackDetails;
import com.sftelehealth.doctor.domain.interactor.GetCaseById;
import com.sftelehealth.doctor.domain.interactor.GetCaseList;
import com.sftelehealth.doctor.domain.interactor.GetMedicineTypes;
import com.sftelehealth.doctor.domain.interactor.GetPrescriptionAndDocumentsForCase;
import com.sftelehealth.doctor.domain.interactor.GetSystemInfo;
import com.sftelehealth.doctor.domain.interactor.GetThisDoctor;
import com.sftelehealth.doctor.domain.interactor.InitiateCall;
import com.sftelehealth.doctor.domain.interactor.InitiateEmergencyCall;
import com.sftelehealth.doctor.domain.interactor.IsUserAuthenticated;
import com.sftelehealth.doctor.domain.interactor.ShouldUpdateApp;
import com.sftelehealth.doctor.domain.interactor.UpdateDoctor;
import com.sftelehealth.doctor.domain.interactor.UpdateDoctorImage;
import com.sftelehealth.doctor.domain.interactor.UpdateSignature;

import javax.inject.Inject;

/**
 * Factory for ViewModels
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    GenerateOTP generateOTP;

    @Inject
    IsUserAuthenticated isUserAuthenticated;

    @Inject
    ConfirmOtp confirmOtp;

    @Inject
    GetCaseList getCaseList;

    @Inject
    GetAppointmentsList getAppointmentsList;

    @Inject
    ShouldUpdateApp shouldUpdateApp;

    @Inject
    GetCaseById getCaseById;

    @Inject
    GetCallbackDetails getCallbackDetails;

    @Inject
    GetPrescriptionAndDocumentsForCase getPrescriptionAndDocumentsForCase;

    @Inject
    GetThisDoctor getThisDoctor;

    @Inject
    AcceptRejectCallback acceptRejectCallback;

    @Inject
    InitiateCall initiateCallback;

    @Inject
    CreatePrescription createPrescription;

    @Inject
    InitiateEmergencyCall initiateEmergencyCall;

    @Inject
    GetMedicineTypes getMedicineTypes;

    @Inject
    UpdateDoctorImage updateDoctorImage;

    @Inject
    UpdateSignature updateSignature;

    @Inject
    UpdateDoctor updateDoctor;

    @Inject
    CallbackRequestFactory callbackRequestFactory;

    @Inject
    GetSystemInfo getSystemInfo;


    public ViewModelFactory(UseCaseComponent useCaseComponent) {
        /*this.useCaseComponent = DaggerUseCaseComponent.builder()
                            .useCaseModule(new UseCaseModule())
                            .build();*/
        useCaseComponent.inject(this);
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginFragmentViewModel.class)) {
            return (T) new LoginFragmentViewModel(generateOTP, isUserAuthenticated, confirmOtp);
        } else if (modelClass.isAssignableFrom(ConfirmationFragmentViewModel.class)) {
            return (T) new ConfirmationFragmentViewModel(generateOTP, confirmOtp);
        } else if (modelClass.isAssignableFrom(CompletedConsultListFragmentViewModel.class)) {
            return (T) new CompletedConsultListFragmentViewModel(getCaseList, updateDoctor, getThisDoctor, confirmOtp);
        } else if (modelClass.isAssignableFrom(CallbackListFragmentViewModel.class)) {
            return (T) new CallbackListFragmentViewModel(getAppointmentsList, shouldUpdateApp, callbackRequestFactory);
        } else if (modelClass.isAssignableFrom(CaseDetailsActivityFragmentViewModel.class)) {
            return (T) new CaseDetailsActivityFragmentViewModel(getCaseById, getPrescriptionAndDocumentsForCase, initiateEmergencyCall, initiateCallback, getThisDoctor, getSystemInfo);   //, getPrescriptionAndDocumentsForCase
        } else if (modelClass.isAssignableFrom(CallbackDetailsActivityFragmentViewModel.class)) {
            return (T) new CallbackDetailsActivityFragmentViewModel(getCallbackDetails, acceptRejectCallback, initiateCallback, initiateEmergencyCall, getThisDoctor, getSystemInfo);
        } else if (modelClass.isAssignableFrom(PrescriptionActivityFragmentViewModel.class)) {
            return (T) new PrescriptionActivityFragmentViewModel(createPrescription, getMedicineTypes, getThisDoctor);
        } else if (modelClass.isAssignableFrom(DoctorProfileFragmentViewModel.class)) {
            return (T) new DoctorProfileFragmentViewModel(getThisDoctor, updateDoctorImage);
        } else if (modelClass.isAssignableFrom(SignatureActivityViewModel.class)) {
            return (T) new SignatureActivityViewModel(updateSignature);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
