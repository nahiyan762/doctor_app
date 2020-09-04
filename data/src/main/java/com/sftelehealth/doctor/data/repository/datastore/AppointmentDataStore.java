package com.sftelehealth.doctor.data.repository.datastore;

import java.util.List;
import java.util.Map;

import com.sftelehealth.doctor.data.model.CallbackRequest;
import io.reactivex.Observable;

/**
 * Created by Rahul on 27/12/17.
 */

public interface AppointmentDataStore {

    Observable<List<CallbackRequest>> getAppointments (String authCode, Map<String, String> params);
}
