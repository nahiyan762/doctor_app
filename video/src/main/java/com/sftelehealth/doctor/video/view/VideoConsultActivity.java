package com.sftelehealth.doctor.video.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.sftelehealth.doctor.video.R;
import com.sftelehealth.doctor.video.agora.IAgoraHelper;
import com.sftelehealth.doctor.video.databinding.ActivityVideoConsultBinding;
import com.sftelehealth.doctor.video.helper.AgoraStatusHelper;
import com.sftelehealth.doctor.video.helper.AnimationHelper;
import com.sftelehealth.doctor.video.helper.CallDataHelper;
import com.sftelehealth.doctor.video.helper.DisplaySizeHelper;
import com.sftelehealth.doctor.video.internal.di.components.CoreVideoComponent;
import com.sftelehealth.doctor.video.internal.di.components.DaggerCoreVideoComponent;
import com.sftelehealth.doctor.video.internal.di.components.DaggerUseCaseComponent;
import com.sftelehealth.doctor.video.internal.di.components.UseCaseComponent;
import com.sftelehealth.doctor.video.internal.di.modules.ActivityModule;
import com.sftelehealth.doctor.video.internal.di.modules.CoreVideoModule;
import com.sftelehealth.doctor.video.internal.di.modules.UseCaseModule;
import com.sftelehealth.doctor.video.service.AgoraHelperService;
import com.sftelehealth.doctor.video.viewmodel.VideoCallViewModel;
import com.sftelehealth.doctor.video.viewmodel.ViewModelFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.sftelehealth.doctor.video.Constant.AGORA_API_LOGIN_FAILED;
import static com.sftelehealth.doctor.video.Constant.AGORA_API_LOGIN_SUCCESS;
import static com.sftelehealth.doctor.video.Constant.AGORA_FIRST_LOCAL_VIDEO_FRAME;
import static com.sftelehealth.doctor.video.Constant.AGORA_RTC_INSTANCE_ACQUIRED;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_EVENT_INVITE_ACCEPTED;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_EVENT_INVITE_DELIVERED;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_EVENT_INVITE_ENDED_BY_MYSELF;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_EVENT_INVITE_ENDED_BY_PEER;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_EVENT_INVITE_FAILED;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_EVENT_INVITE_REFUSED;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_STATE_ENDED;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_STATE_ON_GOING;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_STATE_PAUSED;
import static com.sftelehealth.doctor.video.network.NetworkChangeReceiver.NETWORK_AVAILABLE;

public class VideoConsultActivity extends AppCompatActivity implements ServiceConnection, View.OnTouchListener, View.OnClickListener {

    ActivityVideoConsultBinding viewBinding;
    CoreVideoComponent coreVideoComponent;
    UseCaseComponent useCaseComponent;

    VideoCallViewModel viewModel;

    @Inject
    SharedPreferences sp;
    /*String channelName;
    String userId;
    String patientName;*/
    private final String TAG = "VideoConsultActivity";

    boolean isBoundToService;
    //boolean isButtonShowing = true;

    boolean isNetworkAvailable = true;

    boolean isRemoteViewRendered = false;

    Disposable timerDisposable;

    AnimationHelper layoutAnimationHelper;
    AnimationHelper buttonsAnimationHelper;

    private SurfaceView remoteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate called");

        initializeInjector();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Flags to launch this activity even when the phone is locked
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);

        if (Build.VERSION.SDK_INT < 26)
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_consult);

        viewModel = obtainViewModel(this);

        setUpData();
        setUpObservers();

        viewBinding.videoContainer.remoteVideoViewContainer.setOnTouchListener(this);
        viewBinding.controlsContainer.pauseCall.setOnClickListener(this);
        viewBinding.controlsContainer.endCall.setOnClickListener(this);
    }

    public void log(final String s) {
        super.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewBinding.editTextLog.getText().append(s + "\n");
                viewBinding.editTextLog.scrollTo(0, 1000000000);
            }
        });
    }

    private void initializeInjector() {

        coreVideoComponent = DaggerCoreVideoComponent.builder()
                .coreVideoModule(new CoreVideoModule(this)).build();
        coreVideoComponent.inject(this);

        useCaseComponent = DaggerUseCaseComponent.builder()
                .coreVideoComponent(coreVideoComponent)
                .activityModule(new ActivityModule(this))
                //.androidModule(new AndroidModule(this))
                .useCaseModule(new UseCaseModule()).build();

        // useCaseComponent.inject(this);
    }

    private void setUpData() {

        viewModel.setUpCallData(
                getIntent().getStringExtra("channel_name"),
                String.valueOf(getIntent().getIntExtra("patient_user_id", 0)),
                getIntent().getStringExtra("patient_name"),
                getIntent().getBooleanExtra("isResumed", false)
        );

        layoutAnimationHelper = new AnimationHelper(this);
        buttonsAnimationHelper = new AnimationHelper(this);

        if (!AgoraStatusHelper.isServiceRunning(this))
            startAgoraService();
    }

    private void setUpObservers() {

        viewModel.callDataSetupComplete.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

                Log.i(TAG, "call data setup, bind service called");

                /**
                 * bind to the service and login for receiving events
                 */
                bindToVideoCallService();

                // This should not be called here infact might choose to not call at all
                // Preserve media Channel Key of current call and the call state in the remote service
                // viewModel.getMediaChannelKey(viewModel.callData.getChannelName(), "", viewModel.callData.getPatienUserId());
            }
        });

        viewModel.callEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer callEvent) {
                switch (callEvent) {
                    case VIDEO_CALL_EVENT_INVITE_DELIVERED:
                        setMessage("invite_received");
                        break;
                    case VIDEO_CALL_EVENT_INVITE_ACCEPTED:
                        setMessage("invite_accepted");
                        callAcceptedUI();
                        break;
                    case VIDEO_CALL_EVENT_INVITE_REFUSED:
                        setMessage("invite_rejected");
                        endCall();
                        break;
                    case VIDEO_CALL_EVENT_INVITE_FAILED:
                        setMessage("invite_failed");
                        endCall();
                        break;
                    case VIDEO_CALL_EVENT_INVITE_ENDED_BY_PEER:
                        setMessage("invite_ended");
                        endCall();
                        break;
                    case VIDEO_CALL_EVENT_INVITE_ENDED_BY_MYSELF:
                        setMessage("invite_ended");
                        endCall();
                        break;
                }
            }
        });

        viewModel.agoraAPIEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer agoraAPIEvent) {
                switch (agoraAPIEvent) {
                    case AGORA_API_LOGIN_FAILED:
                        Toast.makeText(VideoConsultActivity.this, "Check internet connection", Toast.LENGTH_LONG).show();
                        endCall();
                        break;
                    case AGORA_API_LOGIN_SUCCESS:
                        break;
                    case AGORA_RTC_INSTANCE_ACQUIRED:
                        renderLocalView();
                        if (getIntent().getBooleanExtra("isResumed", false)) {
                            // If it is resumed then just unMute video and render views with quick animation.
                            unMuteVideoCall();
                        }
                        break;
                    case AGORA_FIRST_LOCAL_VIDEO_FRAME:
                        break;

                }
            }
        });

        viewModel.videoStreamAvailable.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean videoStreamAvailable) {

                Log.d("onRemoteVideoStats", "videoStreamAvailableObserved");
                if (!viewModel.isRemoteVideoViewRendered)
                    renderRemoteView(videoStreamAvailable);

                if (getIntent().getBooleanExtra("isResumed", false)) {

                    viewBinding.controlsContainer.pauseEndCall.setVisibility(View.VISIBLE);

                    viewModel.callData = CallDataHelper.getCallData(sp);
                    viewModel.callData.getState().setVideoCallState(VIDEO_CALL_STATE_ON_GOING);
                    // CallDataHelper.saveCallData(viewModel.callData, sp);
                    // change the call state from pause to on Going and set the blurView
                    manageBlurViewContainer();
                    managePauseText();

                    if (viewModel.isLocalPaused()) {
                        viewBinding.controlsContainer.pauseCall.setText(getString(R.string.resume));
                    } else {
                        viewBinding.controlsContainer.pauseCall.setText(getString(R.string.pause));
                        Observable.timer(0, TimeUnit.SECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnNext(x -> hideControlButtons())
                                .subscribe();
                    }

                    viewModel.callData.getState().setVideoMuted(false);
                    CallDataHelper.saveCallData(viewModel.callData, sp);

                    // viewBinding.videoContainer.localVideoViewContainer.startAnimation(AnimationHelper.getLocalScreenTransition(viewBinding.videoContainer.localVideoViewContainer, DisplaySizeHelper.getDpSizeInPixel(60, VideoConsultActivity.this)));
                }
            }
        });

        viewModel.isLocalPausedStateChanged.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean remoteUserPausedCall) {
                if (viewModel.isLocalPaused()) {
                    viewBinding.controlsContainer.pauseCall.setText(getString(R.string.resume));

                    /*viewBinding.videoContainer.blurViewContainer.setVisibility(View.VISIBLE);
                    showPauseText();*/
                    showControlButtons();
                } else {
                    viewBinding.controlsContainer.pauseCall.setText(getString(R.string.pause));
                    Observable.timer(0, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext(x -> hideControlButtons())
                            .subscribe();
                }
                manageBlurViewContainer();
                managePauseText();
                //saveRemoteViewCanvas();
            }
        });

        viewModel.isRemotePausedStateChanged.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                manageBlurViewContainer();
                managePauseText();
            }
        });

        viewModel.connectivityStatusChange.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer connectivityStatus) {
                if (connectivityStatus == NETWORK_AVAILABLE) {
                    isNetworkAvailable = true;
                } else {
                    isNetworkAvailable = false;
                }
            }
        });
    }

    private void startAgoraService() {
        Intent startVideoCallService = new Intent(this, AgoraHelperService.class);
        startService(startVideoCallService);
    }

    private void bindToVideoCallService() {

        isBoundToService = false;

        if (getIntent().getBooleanExtra("isResumed", false)) {
            setMessage("resuming");
            viewBinding.controlsContainer.infoContainer.messageAttributeText.setText(CallDataHelper.getCallData(sp).getPatientName());
        } else {
            setMessage("initiating");
            viewBinding.controlsContainer.infoContainer.messageAttributeText.setText(CallDataHelper.getCallData(sp).getPatientName());
            viewBinding.controlsContainer.pauseEndCall.setVisibility(View.VISIBLE);
        }

        Intent bindToVideoCallService = new Intent(this, AgoraHelperService.class);
        bindToVideoCallService.putExtra("call_data", viewModel.callData);
        isBoundToService = bindService(bindToVideoCallService, this, Context.BIND_AUTO_CREATE); //|Context.BIND_IMPORTANT

        /*if(isBoundToService)
            initiateRtcEngine();*/
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        // Start video call
        viewModel.setAgoraHelperBinding((IAgoraHelper) service);

        Log.i(TAG, "onServiceConnected called");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i(TAG, "onServiceDisconnected called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume called");
        if (CallDataHelper.getCallData(sp).getState().getVideoCallState() == VIDEO_CALL_STATE_PAUSED)
            viewModel.isLocalPausedStateChanged.postValue(true);
    }

    @Override
    protected void onPause() {
        viewModel.callData = CallDataHelper.getCallData(sp);
        if (!(CallDataHelper.getCallData(sp).getState().getVideoCallState() == VIDEO_CALL_STATE_ENDED) && !(CallDataHelper.getCallData(sp).getState().getVideoCallState() == VIDEO_CALL_STATE_PAUSED) && !(CallDataHelper.getCallData(sp).getState().getPhoneState() == TelephonyManager.CALL_STATE_OFFHOOK || viewModel.callData.getState().getPhoneState() == TelephonyManager.CALL_STATE_RINGING))
            muteVideoCall();
        else if (CallDataHelper.getCallData(sp).getState().getPhoneState() == TelephonyManager.CALL_STATE_OFFHOOK || CallDataHelper.getCallData(sp).getState().getPhoneState() == TelephonyManager.CALL_STATE_RINGING)
            viewModel.toggleLocalAudioVideoStreams();

        Log.i(TAG, "onPause called");

        super.onPause();
    }

    @Override
    protected void onDestroy() {

        Log.i(TAG, "onDestroy called");

        if (isBoundToService)
            unbindService(this);

        super.onDestroy();
    }

    public VideoCallViewModel obtainViewModel(FragmentActivity activity) {
        if (viewModel == null) {
            // Use a Factory to inject dependencies into the ViewModel
            ViewModelFactory factory = new ViewModelFactory(useCaseComponent, sp);
            viewModel = ViewModelProviders.of(activity, factory).get(VideoCallViewModel.class);
        }
        return viewModel;
    }

    // Helper functions for video consult
    // AgoraMediaHelper should be able to handle media related service, State Object for the call

    // UI actions

    private void renderLocalView() {
        // can be later used to give a video disable option
        boolean isVideoEnabled = true;

        SurfaceView localView;

        if (isVideoEnabled) {
            viewModel.m_agoraMedia.enableVideo();

            viewBinding.videoContainer.localVideoViewContainer.removeAllViews();
            localView = viewModel.m_agoraMedia.CreateRendererView(this);
            localView.setZOrderMediaOverlay(false);
            viewBinding.videoContainer.localVideoViewContainer.addView(localView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            int successCode = viewModel.m_agoraMedia.setupLocalVideo(new VideoCanvas(localView));

            if (getIntent().getBooleanExtra("isResumed", false)) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (successCode < 0) {
                new android.os.Handler().postDelayed(() -> {
                    viewModel.m_agoraMedia.setupLocalVideo(new VideoCanvas(localView));
                    localView.invalidate();
                }, 1000);
            }

            // setup Audio Profile
            // viewModel.m_agoraMedia.setAudioProfile(Constants.AUDIO_PROFILE_MUSIC_HIGH_QUALITY, Constants.AUDIO_SCENARIO_CHATROOM_GAMING);

            viewModel.m_agoraMedia.startPreview();

        } else {
            viewModel.m_agoraMedia.disableVideo();
        }
    }

    private void renderRemoteView(boolean videoStreamAvailable) {

        if (getIntent().getBooleanExtra("isResumed", false))
            viewBinding.controlsContainer.infoContainer.infoContainerLayout.setVisibility(View.GONE);   //  AnimationHelper.hideDown(viewBinding.controlsContainer.infoContainer.infoContainerLayout, true);

        if (videoStreamAvailable) {

            if (remoteView == null) {
                remoteView = RtcEngine.CreateRendererView(this);
                remoteView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
                // remoteView.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_grey));
                remoteView.setZOrderMediaOverlay(false);
                viewBinding.videoContainer.remoteVideoViewContainer.removeAllViews();
                viewBinding.videoContainer.remoteVideoViewContainer.addView(remoteView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            }

            int successCode = viewModel.m_agoraMedia.setupRemoteVideo(new VideoCanvas(remoteView, VideoCanvas.RENDER_MODE_HIDDEN, viewModel.videoPeerId));

            if (successCode < 0) {
                new android.os.Handler().postDelayed(() -> {
                    viewModel.m_agoraMedia.setupRemoteVideo(new VideoCanvas(remoteView, VideoCanvas.RENDER_MODE_HIDDEN, viewModel.videoPeerId));
                    remoteView.invalidate();
                }, 500);
            }

            // Video started for the first time or after a call was resumed from paused state
            Observable.timer(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(x -> layoutAnimationHelper.transformLocalVideoSize(viewBinding.videoContainer.localVideoViewContainer, DisplaySizeHelper.getDpSizeInPixel(60, this), DisplaySizeHelper.getDpSizeInPixel(60, this)))
                    .subscribe();

            // viewBinding.videoContainer.localVideoViewContainer.startAnimation(AnimationHelper.getLocalScreenTransition(viewBinding.videoContainer.localVideoViewContainer, DisplaySizeHelper.getDpSizeInPixel(60, this)));

            Observable.timer(0, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(x -> hideControlButtons())
                    .subscribe();
        } else {
            // else show message that video is paused
        }

        viewModel.isRemoteVideoViewRendered = true;
    }

    /*private void pauseCall() {
        // use the data "isLocalCallPaused" to control the UI in accordance with
        // mute local data streams
        // Check the state of the remote video and audio data and current state of the call to update the UI
        if(viewModel.isLocalPaused) {

        }

        if(!(CallDataHelper.getCallData(sp).getState().getVideoCallState() == VIDEO_CALL_STATE_PAUSED)) {
            pauseVideoAudioCall();
            // showPausedVideoAudioCallUI();
            // showPauseText();
        } else {
            resumeVideoAudioCall();
            // showResumedVideoAudioCallUI();
        }

        manageBlurViewContainer();
        managePauseText();
    }*/

    private void endCall() {
        // If call is going on then first end invite
        // then leave channel and destroy the media SDK instance for it
        // Change the state here, (shifted to service on endInvite())
        viewModel.callData = CallDataHelper.getCallData(sp);
        viewModel.callData.getState().setVideoCallState(VIDEO_CALL_STATE_ENDED);
        CallDataHelper.saveCallData(viewModel.callData, sp);

        CallDataHelper.notifyAgoraCallStatus(getApplicationContext());

        // CallDataHelper.notifyAgoraCallStatus(getApplicationContext());

        /*try {
            // if invite is onGoing, change of state happening in service
            viewModel.agoraHelperBinding.endInvite();
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/

        /*if(viewModel.m_agoraMedia != null) {
            viewModel.m_agoraMedia.leaveChannel();
            viewModel.m_agoraMedia.destroy();
        }*/

        this.finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.end_call) {
            try {
                viewModel.agoraHelperBinding.endInvite();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            endCall();
        } else if (v.getId() == R.id.pause_call) {
            viewModel.toggleLocalAudioVideoStreams(); // pauseCall();
        } else if (v.getId() == R.id.redial_call) {
            // redialCall();
        } else if (v.getId() == R.id.cancel_call) {
            // cancelCall();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /*if (!isButtonShowing) {

        }*/
        showControlButtons();

        if (timerDisposable != null && !timerDisposable.isDisposed())
            timerDisposable.dispose();

        timerDisposable = Observable.timer(6, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (!(CallDataHelper.getCallData(sp).getState().getVideoCallState() == VIDEO_CALL_STATE_PAUSED))
                            hideControlButtons();
                    }
                });
        return false;
    }

    @Override
    public void onBackPressed() {

        if (!isNetworkAvailable) {
            endCall();
        } else {
            super.onBackPressed();
        }
    }

    /*private void showPauseText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(viewModel.isRemotePaused() && CallDataHelper.getCallData(sp).getState().getVideoCallState() == VIDEO_CALL_STATE_PAUSED) {
                    viewBinding.videoContainer.pauseText.setText("paused");
                } else if (viewModel.isRemotePaused()) {
                    viewBinding.videoContainer.pauseText.setText("paused by\n" + viewModel.callData.getPatientName());
                } else {
                    viewBinding.videoContainer.pauseText.setText("paused by\n" + viewModel.callData.getDoctorName());
                }
            }
        });
    }*/

    private void manageBlurViewContainer() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (viewModel.isRemotePaused() || viewModel.isLocalPaused() || viewModel.remoteVideoMuted)
                    viewBinding.videoContainer.blurViewContainer.setVisibility(View.VISIBLE);
                else
                    viewBinding.videoContainer.blurViewContainer.setVisibility(View.GONE);
            }
        });
    }

    private void managePauseText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (viewModel.isRemotePaused() && viewModel.isLocalPaused()) {
                    viewBinding.videoContainer.pauseText.setText("paused");
                } else if (viewModel.isLocalPaused()) {
                    viewBinding.videoContainer.pauseText.setText("paused by\n" + viewModel.callData.getDoctorName());
                } else if (viewModel.isRemotePaused()) {
                    viewBinding.videoContainer.pauseText.setText("paused by\n" + viewModel.callData.getPatientName());
                } else if (!viewModel.isLocalPaused() && viewModel.remoteVideoMuted) {
                    viewBinding.videoContainer.pauseText.setText("video paused by\n" + viewModel.callData.getPatientName());
                } else {
                    viewBinding.videoContainer.pauseText.setText("");
                }
            }
        });
    }

    private void saveRemoteViewCanvas() {

    }

    private void hideControlButtons() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (viewBinding.controlsContainer.buttonsContainer.getVisibility() == View.VISIBLE)
                    buttonsAnimationHelper.hideDown(viewBinding.controlsContainer.buttonsContainer, false);
            }
        });
        /*if (viewBinding.controlsContainer.buttonsContainer.getVisibility() == View.VISIBLE)
            buttonsAnimationHelper.hideDown(viewBinding.controlsContainer.buttonsContainer, false);*/
        // isButtonShowing = false;
    }

    private void showControlButtons() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (viewBinding.controlsContainer.buttonsContainer.getVisibility() == View.GONE || viewBinding.controlsContainer.buttonsContainer.getVisibility() == View.INVISIBLE)
                    buttonsAnimationHelper.showUp(viewBinding.controlsContainer.buttonsContainer);
            }
        });
        /*if (viewBinding.controlsContainer.buttonsContainer.getVisibility() == View.GONE || viewBinding.controlsContainer.buttonsContainer.getVisibility() == View.INVISIBLE)
            buttonsAnimationHelper.showUp(viewBinding.controlsContainer.buttonsContainer);*/
        // isButtonShowing = true;
    }

    private void callAcceptedUI() {

        // infoContainer.setVisibility(View.GONE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layoutAnimationHelper.hideDownLayout(viewBinding.controlsContainer.infoContainer.infoContainerLayout, true);
            }
        });
    }

    /*public void pauseVideoAudioCall() {

        // viewModel.m_agoraMedia.muteAllRemoteAudioStreams(true);
        // viewModel.m_agoraMedia.muteAllRemoteVideoStreams(true);

        viewModel.m_agoraMedia.muteLocalVideoStream(true);
        viewModel.m_agoraMedia.muteLocalAudioStream(true);

        CallData callData = CallDataHelper.getCallData(sp);
        callData.getState().setVideoCallState(VIDEO_CALL_STATE_PAUSED);

        CallDataHelper.saveCallData(callData, sp);
    }

    public void resumeVideoAudioCall() {

        // viewModel.m_agoraMedia.muteAllRemoteAudioStreams(false);
        // viewModel.m_agoraMedia.muteAllRemoteVideoStreams(false);

        viewModel.m_agoraMedia.muteLocalVideoStream(false);
        viewModel.m_agoraMedia.muteLocalAudioStream(false);

        CallData callData = CallDataHelper.getCallData(sp);
        callData.getState().setVideoCallState(VIDEO_CALL_STATE_ON_GOING);

        CallDataHelper.saveCallData(callData, sp);
    }*/

    /*private void showPausedVideoAudioCallUI() {
        viewBinding.controlsContainer.pauseCall.setText("Resume");
        viewBinding.videoContainer.blurViewContainer.setVisibility(View.VISIBLE);
    }

    private void showResumedVideoAudioCallUI() {
        viewBinding.controlsContainer.pauseCall.setText("Pause");
        viewBinding.videoContainer.blurViewContainer.setVisibility(View.GONE);
    }*/

    public void muteVideoCall() {

        viewModel.m_agoraMedia.muteAllRemoteVideoStreams(true);
        viewModel.m_agoraMedia.muteLocalVideoStream(true);

        viewModel.callData = CallDataHelper.getCallData(sp);
        viewModel.callData.getState().setVideoCallState(VIDEO_CALL_STATE_PAUSED);
        viewModel.callData.getState().setVideoMuted(true);
        CallDataHelper.saveCallData(viewModel.callData, sp);

        viewModel.isRemoteVideoViewRendered = false;

        viewModel.agoraHelperBinding.callStatusChanged();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void unMuteVideoCall() {

        viewModel.m_agoraMedia.muteAllRemoteVideoStreams(false);
        viewModel.m_agoraMedia.muteLocalVideoStream(false);

        viewModel.callData = CallDataHelper.getCallData(sp);
        viewModel.callData.getState().setVideoCallState(VIDEO_CALL_STATE_ON_GOING);
        viewModel.callData.getState().setVideoMuted(false);
        CallDataHelper.saveCallData(viewModel.callData, sp);

        viewModel.agoraHelperBinding.callStatusChanged();


    }

    private void setMessage(String state) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String message = "";
                switch (state) {
                    case "initiating":
                        message = "initiating video call to";
                        break;
                    case "resuming":
                        message = "resuming on going call";
                        break;
                    case "logged_in":
                        message = "Connecting to";
                        break;
                    case "invite_received":
                        message = "ringing";
                        break;
                    case "invite_sent":
                        message = "Connecting to";
                        break;
                    case "invite_accepted":
                        message = "Joining call";
                        break;
                    case "invite_failed":
                        message = "Could not reach patient";
                        break;
                    case "invite_rejected":
                        message = "Call rejected by patient";
                        break;
                    case "invite_ended":
                        message = "Call ended by Patient";
                        break;
                    default:
                        message = "Some network error occurred";
                }
                viewBinding.controlsContainer.infoContainer.messageTypeText.setText(message);
            }
        });
    }
}
