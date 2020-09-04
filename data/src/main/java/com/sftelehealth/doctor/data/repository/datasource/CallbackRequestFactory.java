package com.sftelehealth.doctor.data.repository.datasource;

import androidx.paging.DataSource;
import android.content.Context;

import com.sftelehealth.doctor.data.model.CallbackRequest;

import javax.inject.Inject;

public class CallbackRequestFactory extends DataSource.Factory<Integer, CallbackRequest> {

    Context context;

    @Inject
    CallbackRequestFactory(Context context) {
        this.context = context;
    }

    @Override
    public DataSource<Integer, CallbackRequest> create() {
        return new CallbackRequestListDataSource(context);
    }
}
