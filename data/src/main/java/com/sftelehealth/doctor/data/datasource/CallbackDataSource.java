package com.sftelehealth.doctor.data.datasource;

import androidx.paging.PageKeyedDataSource;
import androidx.annotation.NonNull;

public class CallbackDataSource extends PageKeyedDataSource {
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams params, @NonNull LoadCallback callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams params, @NonNull LoadCallback callback) {

    }
    /*private static String TAG="WP DATA SOURCE";
    int page=1;
    int max_page;
    WPRestAPI wordPressService;
    LoadInitialParams initialParams;
    // ItemKeyedDataSource.LoadInitialParams initialParams;
    LoadParams afterParams;
    private MutableLiveData networkState;
    private MutableLiveData initialLoading;
    private Executor retryExecutor;
    public CallbackDataSource(Executor retryExecutor){
        *//*wordPressService=WPRestAPI.getInstance();
        networkState=new MutableLiveData();
        initialLoading=new MutableLiveData();
        this.retryExecutor=retryExecutor;*//*
    }
    public MutableLiveData getNetworkState() {
        return networkState;
    }
    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback callback) {
        Log.i(TAG, "Loading Page " + 1 + " Load Size " + params.requestedLoadSize);
        final List postList =new ArrayList<>();
        initialParams=params;
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        wordPressService.getAllPost(page).enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> call, Response<List> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    postList.addAll(response.body());
                    String totalPage=response.headers().get("X-WP-TotalPages");
                    max_page=Integer.parseInt(totalPage);
                    Log.d(TAG,"Total page is " + totalPage);
                    callback.onResult(postList,null,page+1);
                    initialLoading.postValue(NetworkState.LOADED);
                    networkState.postValue(NetworkState.LOADED);
                    initialParams = null;
                } else {
                    Log.e("WP API CALL", response.message());
                    initialLoading.postValue(new NetworkState(Status.FAILED, response.message()));
                    networkState.postValue(new NetworkState(Status.FAILED, response.message()));
                }
            }
            @Override
            public void onFailure(Call<List> call, Throwable t) {
                String errorMessage;
                errorMessage = t.getMessage();
                if (t == null) {
                    errorMessage = "error";
                }
                networkState.postValue(new NetworkState(Status.FAILED, errorMessage));
            }
        });
    }
    @Override
    public void loadBefore(@NonNull LoadParams params, @NonNull LoadCallback callback) {
    }
    @Override
    public void loadAfter(@NonNull LoadParams params, @NonNull LoadCallback callback) {
        Log.i(TAG, "Loading Page " + params.key + " Size " + params.requestedLoadSize);
        page=page+1;
        final List postList =new ArrayList<>();
        afterParams=params;
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        wordPressService.getAllPost((int) params.key).enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> call, Response<List> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    postList.addAll(response.body());
// callback.onResult(postList);
                    callback.onResult(postList, page + 1);
                    initialLoading.postValue(NetworkState.LOADED);
                    networkState.postValue(NetworkState.LOADED);
                    initialParams = null;
                }
                else if((int)params.key>max_page) {
                    Log.e("WP API CALL", response.message()+(int)params.key);
                    initialLoading.postValue(new NetworkState(Status.MAX,response.message()));
                    networkState.postValue(new NetworkState(Status.MAX, response.message()));
                }
                else {
                    Log.e("WP API CALL", response.message());
                    initialLoading.postValue(new NetworkState(Status.FAILED, response.message()));
                    networkState.postValue(new NetworkState(Status.FAILED, response.message()));
                }
            }
            @Override
            public void onFailure(Call<List> call, Throwable t) {
                String errorMessage;
                errorMessage = t.getMessage();
                if (t == null) {
                    errorMessage = " error";
                }
                networkState.postValue(new NetworkState(Status.FAILED, errorMessage));
            }
        });
    }*/
}