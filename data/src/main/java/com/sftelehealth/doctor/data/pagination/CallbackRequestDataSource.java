package com.sftelehealth.doctor.data.pagination;

import com.sftelehealth.doctor.data.model.CallbackRequest;
import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.repository.CallbackRequestRepository;

import androidx.paging.PageKeyedDataSource;
import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rahul on 29/01/18.
 */

public class CallbackRequestDataSource extends PageKeyedDataSource<Map<String, String>, CallbackRequest> {

    CallbackRequestRepository callbackRequestRepository;

    CallbackRequestDataSource(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, CallbackRequestRepository callbackRequestRepository) {
        this.callbackRequestRepository = callbackRequestRepository;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Map<String, String>> params, @NonNull LoadInitialCallback<Map<String, String>, CallbackRequest> callback) {

        Map<String, String> apiParameters = new HashMap<>();
        apiParameters.put("page", "1");
        apiParameters.put("size", "" + params.requestedLoadSize);
        // callbackRequestRepository.getCallbacks(apiParameters)
        // callback.onResult(callbackRequestRepository.getCallbacks(apiParameters));;
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Map<String, String>> params, @NonNull LoadCallback<Map<String, String>, CallbackRequest> callback) {

        // Nothing to load here as we only append to the initial data.
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Map<String, String>> params, @NonNull LoadCallback<Map<String, String>, CallbackRequest> callback) {
        // params.key.get("")
        // params.key.get()
        // callbackRequestRepository.getCallbacks();
        // callback.
    }
}