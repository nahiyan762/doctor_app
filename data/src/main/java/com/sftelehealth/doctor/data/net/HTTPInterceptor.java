package com.sftelehealth.doctor.data.net;

import android.content.Intent;

import java.io.IOException;

import com.sftelehealth.doctor.data.DataModule;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

/**
 * Created by Rahul on 04/11/17.
 */

public class HTTPInterceptor implements Interceptor {

    public static final String LOGIN_INTENT = "com.traktion.doctor.intent.action.LOGIN";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();


        Response response;
        try {
            response = chain.proceed(request);
            if(response.code() == HTTP_UNAUTHORIZED || response.code() == HTTP_FORBIDDEN) {
                // send a broadcast with a message to redirect to a
                response.close();
                reLoginUser();
            }
        } catch (Exception e) {
            //logger.log("<-- HTTP FAILED: " + e);
            throw e;
        }
        return response;
    }

    private void reLoginUser() {

        // clear the token that is being used for login
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(LOGIN_INTENT);

        //intent.setClass(DataModule.getInstance().getApplicationContext(), LoginActivity.class);
        //DataModule.getInstance().getApplicationContext().startActivity();

        DataModule.getInstance().getApplicationContext().sendBroadcast(intent);
    }
}
