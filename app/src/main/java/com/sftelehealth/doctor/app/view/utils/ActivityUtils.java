package com.sftelehealth.doctor.app.view.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.utils.Constant;
import com.sftelehealth.doctor.app.view.activity.BaseAppCompatActivity;
import com.sftelehealth.doctor.video.agora.call.CallData;
import com.sftelehealth.doctor.video.helper.AgoraStatusHelper;
import com.sftelehealth.doctor.video.helper.CallDataHelper;
import com.sftelehealth.doctor.video.view.VideoConsultActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_STATE_ENDED;

/**
 * Created by Rahul on 22/06/17.
 * Utility functions for activity
 */

public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        //checkNotNull(fragmentManager);
        //checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivityAndBackstack (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        //checkNotNull(fragmentManager);
        //checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, String tag) {
        //checkNotNull(fragmentManager);
        //checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * To setup a toolbar for an activity
     * @param activity
     * @param title
     */
    public static void setUpToolbar(BaseAppCompatActivity activity, String title) {

        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    /**
     * To setup a toolbar for an activity
     * @param activity
     * @param title
     */
    public static void setUpToolbarForBackPress(BaseAppCompatActivity activity, String title) {

        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }

    /**
     * To setup a toolbar for an activity
     * @param activity
     * @param title
     */
    public static void setUpToolbarWithoutBackButton(BaseAppCompatActivity activity, String title) {

        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setHomeButtonEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    public static BroadcastReceiver getVideoCallStatusReceiver(AgoraCallStatusChangeListener listener) {

        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent ) {
                listener.onStatusChange(1);
            }
        };
    }

    public interface AgoraCallStatusChangeListener {
        void onStatusChange(int status);
    }

    public static void showHideStatusBarCallStatusBar(Activity activity) {
        // check if a call is going on, if yes then show the toolbar
        View view  = activity.findViewById(R.id.call_status_bar);
        if(CallDataHelper.getCallData(((BaseAppCompatActivity)activity).getSharedPreferences()).getState().isVideoCallOnGoing() && AgoraStatusHelper.isServiceRunning(activity)) {

            ((BaseAppCompatActivity)activity).callInProgress = true;

            view.setVisibility(View.VISIBLE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to video call activity
                    CallData callData = CallDataHelper.getCallData(((BaseAppCompatActivity)activity).getSharedPreferences());
                    Intent startVideoConsultActivity = new Intent(activity, VideoConsultActivity.class);
                    startVideoConsultActivity.putExtra(Constant.VIDEO_CALL_STATE, Constant.VIDEO_CALL_INITIATE);
                    startVideoConsultActivity.putExtra("channel_name", callData.getChannelName());
                    startVideoConsultActivity.putExtra("doctor_id", callData.getDoctorId());
                    startVideoConsultActivity.putExtra("doctor_name", callData.getDoctorName());
                    startVideoConsultActivity.putExtra("doctor_image", callData.getDoctorImage());
                    startVideoConsultActivity.putExtra("patient_name", callData.getPatientName());
                    startVideoConsultActivity.putExtra("patient_user_id", callData.getPatientUserId());
                    startVideoConsultActivity.putExtra("isResumed", true);
                    activity.startActivity(startVideoConsultActivity);
                }
            });
        } else {
            ((BaseAppCompatActivity)activity).callInProgress = false;
            CallData callData = CallDataHelper.getCallData(((BaseAppCompatActivity)activity).getSharedPreferences());
            callData.getState().setVideoCallState(VIDEO_CALL_STATE_ENDED);
            CallDataHelper.saveCallData(callData, ((BaseAppCompatActivity)activity).getSharedPreferences());
            view.setVisibility(View.GONE);
        }
    }
}
