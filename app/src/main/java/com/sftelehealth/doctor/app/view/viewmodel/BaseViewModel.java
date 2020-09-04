package com.sftelehealth.doctor.app.view.viewmodel;

import androidx.databinding.BaseObservable;

/**
 * Created by Rahul on 24/06/17.
 */

public abstract class BaseViewModel extends BaseObservable {

    protected abstract void onStart();

    protected abstract void onStop();

    protected abstract void onDataLoading();

    protected abstract void onDataLoaded();

    protected abstract void onNetworkNotAvailable();



}
