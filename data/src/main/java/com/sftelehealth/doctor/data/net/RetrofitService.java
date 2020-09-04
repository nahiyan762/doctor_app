package com.sftelehealth.doctor.data.net;


import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.sftelehealth.doctor.data.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rahul on 23/07/16.
 */
public class RetrofitService<S> {

    private String API_BASE_URL;

    private int TIMEOUT = 10000;

    // set your desired log level
    private HttpLoggingInterceptor logging;

    private OkHttpClient.Builder httpClient;

    private Retrofit.Builder builder;

    private Retrofit retrofit;

    // Save the token and other User details in the Shared Preferences
    //SharedPreferences sp = SharedPreferences.Editor.getSharedPreferences(Constant.USER_PREFS, Context.MODE_PRIVATE);

    // set your desired log level
    //private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    // add your other interceptors
    // add logging as last interceptor
    //private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(logging);


    /*private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());*/

    private <S> S createService(Class<S> serviceClass) {

        API_BASE_URL =  BuildConfig.HOST_NAME;   // "https://api-staging-v2.doctor24x7.in/api/v1/";  //

        logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.MICROSECONDS)
                    .readTimeout(TIMEOUT + 20000, TimeUnit.MILLISECONDS);

        /*httpClient.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        httpClient.writeTimeout(TIMEOUT, TimeUnit.MICROSECONDS);
        httpClient.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS);*/
        //httpClient.retryOnConnectionFailure(false);
        if(BuildConfig.DEBUG) {
            httpClient
                    // add Stetho only for Debug builds
                    .addNetworkInterceptor(new StethoInterceptor())
                    .addInterceptor(new Interceptor() {

                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request request = original.newBuilder()
                                    //.header("Content-Type", "application/json")
                                    .method(original.method(), original.body())
                                    .build();

                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(logging);
        } else {
            httpClient
                    .addInterceptor(new Interceptor() {

                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request request = original.newBuilder()
                                    //.header("Content-Type", "application/json")
                                    .method(original.method(), original.body())
                                    .build();

                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(logging);
        }

        builder = new Retrofit.Builder();
        builder.baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create());

        retrofit = builder.client(httpClient.build()).build();

        Retrofit retrofit = builder.client(httpClient.build()).build();
        //apiService = (RestApi) retrofit.create(serviceClass);

        return retrofit.create(serviceClass);
    }

    private CoreApi coreApiService;

    /**
     * This function will provide the core APIs
     * @return CoreApi
     */
    public synchronized CoreApi getCoreApiService() {
        if (coreApiService == null) {
            coreApiService = this.createService(CoreApi.class);
        }
        return coreApiService;
    }

    private RestApi restApiService;
    /**
     * This function will provide the other rest APIs which are not the part of core APIs
     * @return CoreApi
     */
    public synchronized RestApi getRestApiService() {
        if (restApiService == null) {
            restApiService = this.createService(RestApi.class);
        }
        return restApiService;
    }
}
