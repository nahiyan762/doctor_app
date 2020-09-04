package com.sftelehealth.doctor.data.repository.datasource;

import androidx.paging.PositionalDataSource;
import android.content.Context;
import androidx.annotation.NonNull;

import com.sftelehealth.doctor.data.model.CallbackRequest;
import com.sftelehealth.doctor.data.net.AuthCodeProvider;
import com.sftelehealth.doctor.data.net.CoreApi;
import com.sftelehealth.doctor.data.net.RetrofitService;

import java.util.HashMap;

import javax.inject.Inject;

public class CallbackRequestListDataSource extends PositionalDataSource<CallbackRequest> {

    private final Context context;
    boolean isUserRegistered;
    boolean isCached = false;

    RetrofitService restService = new RetrofitService();
    CoreApi coreApi = restService.getCoreApiService();

    @Inject
    public CallbackRequestListDataSource(Context context) {
        this.context = context;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<CallbackRequest> callback) {
        HashMap<String, String> pagingParams = new HashMap<>();
        pagingParams.put("page","1");   //+ params.requestedStartPosition
        pagingParams.put("size", String.valueOf(params.pageSize));

        coreApi.getAppointments(
                AuthCodeProvider.getAuthCode(context),
                pagingParams).map(callbackRequestResponse -> {
            callback.onResult(callbackRequestResponse.getRequests(),
                    0);
                    return "";
        });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<CallbackRequest> callback) {
        HashMap<String, String> pagingParams = new HashMap<>();

        //the page number is directly dependent on the load size in a way that
        // to access a particular range if
        int page = (params.startPosition / params.loadSize);
        int skippedItems = params.startPosition % params.loadSize;

        pagingParams.put("page", String.valueOf(page));
        pagingParams.put("size", "" + params.loadSize);


        coreApi.getAppointments(AuthCodeProvider.getAuthCode(context),
                pagingParams).map(callbackRequestResponse -> {

                    for(int i=0; i<skippedItems; i++) {
                        callbackRequestResponse.getRequests().remove(0);
                    }
                    callback.onResult(callbackRequestResponse.getRequests());
                    return "";
        });
    }
}
