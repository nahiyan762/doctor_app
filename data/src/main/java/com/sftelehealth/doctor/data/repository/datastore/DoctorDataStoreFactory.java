package com.sftelehealth.doctor.data.repository.datastore;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sftelehealth.doctor.data.database.DatabaseObject;
import com.sftelehealth.doctor.data.database.mapper.DBDoctorEntityMapper;
import com.sftelehealth.doctor.data.model.Doctor;
import com.sftelehealth.doctor.data.net.AuthCodeProvider;
import com.sftelehealth.doctor.data.net.CoreApi;
import com.sftelehealth.doctor.data.net.ProgressRequestBody;
import com.sftelehealth.doctor.data.net.RetrofitService;

import java.io.File;
import java.util.HashMap;

import javax.inject.Inject;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Rahul on 16/01/18.
 */

public class DoctorDataStoreFactory implements DoctorDataStore {

    private final Context context;
    boolean isUserRegistered;
    boolean isCached = false;

    RetrofitService restService = new RetrofitService();
    CoreApi coreApi = restService.getCoreApiService();

    @Inject
    public DoctorDataStoreFactory(Context context) {
        this.context = context;
    }

    @Override
    public Observable<String> doctorSignatureUpdate(HashMap<String, String> params) {

        String doctorId = params.get("doctor_id");
        File userImageFile = new File(params.get("file_path"));

        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), doctorId);
        RequestBody modelProperty = RequestBody.create(MediaType.parse("multipart/form-data"), "signatureImage");
        RequestBody path = RequestBody.create(MediaType.parse("multipart/form-data"), "DOCTOR_SIGNATURE_PATH");
        RequestBody model = RequestBody.create(MediaType.parse("multipart/form-data"), "Doctor");

        // create RequestBody instance from file
        // RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), userImageFile);
        // MultipartBody.Part image = MultipartBody.Part.createFormData("image", userImageFile.getName(), requestFile);

        ProgressRequestBody requestFile = new ProgressRequestBody(userImageFile, new ProgressRequestBody.UploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage) {
                Intent statusUpdateIntent = new Intent("doctor_image_upload_status_broadcast");
                statusUpdateIntent.putExtra("upload_status", percentage);
                LocalBroadcastManager.getInstance(context).sendBroadcast(statusUpdateIntent);
                Log.d("upload_status", "uploading: " + percentage);
            }

            @Override
            public void onFinish() {
                Intent statusCompleteIntent = new Intent("doctor_image_upload_status_broadcast");
                statusCompleteIntent.putExtra("upload_status", 100);
                LocalBroadcastManager.getInstance(context).sendBroadcast(statusCompleteIntent);
                Log.d("upload_status", "completed: ");
            }
        });

        MultipartBody.Part image = MultipartBody.Part.createFormData("image", userImageFile.getName(), requestFile);

        return coreApi.doctorImageUpdate(AuthCodeProvider.getAuthCode(context), id, modelProperty, path, model, image).map(doctorImageUpdateResponse -> {
            DatabaseObject.getInstance(context).doctorDao().setSignature(doctorImageUpdateResponse.getPath(), Integer.parseInt(doctorId));
            return  doctorImageUpdateResponse.getPath();
        });
    }

    @Override
    public Observable<String> doctorImageUpdate(HashMap<String, String> params) {

        String doctorId = params.get("doctor_id");
        File userImageFile = new File(params.get("file_path"));

        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), doctorId);
        RequestBody modelProperty = RequestBody.create(MediaType.parse("multipart/form-data"), "image");
        RequestBody path = RequestBody.create(MediaType.parse("multipart/form-data"), "DOCTOR_IMAGE_PATH");
        RequestBody model = RequestBody.create(MediaType.parse("multipart/form-data"), "Doctor");

        // create RequestBody instance from file
        // RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), userImageFile);
        // MultipartBody.Part image = MultipartBody.Part.createFormData("image", userImageFile.getName(), requestFile);

        ProgressRequestBody requestFile = new ProgressRequestBody(userImageFile, new ProgressRequestBody.UploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage) {
                Intent statusUpdateIntent = new Intent("doctor_image_upload_status_broadcast");
                statusUpdateIntent.putExtra("upload_status", percentage);
                LocalBroadcastManager.getInstance(context).sendBroadcast(statusUpdateIntent);
                Log.d("upload_status", "uploading: " + percentage);
            }

            @Override
            public void onFinish() {
                Intent statusCompleteIntent = new Intent("doctor_image_upload_status_broadcast");
                statusCompleteIntent.putExtra("upload_status", 100);
                LocalBroadcastManager.getInstance(context).sendBroadcast(statusCompleteIntent);
                Log.d("upload_status", "completed: ");
            }
        });

        MultipartBody.Part image = MultipartBody.Part.createFormData("image", userImageFile.getName(), requestFile);
        //uploadingProgress.setVisibility(View.VISIBLE);

        return coreApi.doctorImageUpdate(AuthCodeProvider.getAuthCode(context), id, modelProperty, path, model, image).map(doctorImageUpdateResponse -> {
            DatabaseObject.getInstance(context).doctorDao().setImage(doctorImageUpdateResponse.getPath(), Integer.parseInt(doctorId));
            return  doctorImageUpdateResponse.getPath();
        });
    }

    @Override
    public Observable<Doctor> getDoctor() {
        DBDoctorEntityMapper mapper = new DBDoctorEntityMapper();

        return Observable.just(mapper)
                .subscribeOn(Schedulers.io())
                .map(dbMapperInstance -> {
                    return dbMapperInstance.transformToData(DatabaseObject.getInstance(context)
                            .doctorDao()
                            .getDoctor());
                });
    }
}
