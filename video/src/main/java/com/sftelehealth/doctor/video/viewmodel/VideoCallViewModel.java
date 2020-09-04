package com.sftelehealth.doctor.video.viewmodel;

import android.content.SharedPreferences;
import android.os.RemoteException;
import android.util.Log;

import com.sftelehealth.doctor.domain.interactor.GetThisDoctor;
import com.sftelehealth.doctor.domain.model.Doctor;
import com.sftelehealth.doctor.video.Constant;
import com.sftelehealth.doctor.video.agora.AgoraAPICallback;
import com.sftelehealth.doctor.video.agora.IAgoraHelper;
import com.sftelehealth.doctor.video.agora.call.CallData;
import com.sftelehealth.doctor.video.agora.call.State;
import com.sftelehealth.doctor.video.agora.message.AgoraMessageHelper;
import com.sftelehealth.doctor.video.helper.CallDataHelper;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.agora.rtc.RtcEngine;
import io.reactivex.observers.DisposableObserver;

import static com.sftelehealth.doctor.video.Constant.AGORA_API_LOGIN_FAILED;
import static com.sftelehealth.doctor.video.Constant.AGORA_API_LOGIN_SUCCESS;
import static com.sftelehealth.doctor.video.Constant.AGORA_FIRST_LOCAL_VIDEO_FRAME;
import static com.sftelehealth.doctor.video.Constant.AGORA_RTC_INSTANCE_ACQUIRED;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_STATE_ON_GOING;
import static com.sftelehealth.doctor.video.Constant.VIDEO_CALL_STATE_PAUSED;
import static com.sftelehealth.doctor.video.Constant.VIDEO_MESSAGE_ON_ANOTHER_CALL;

public class VideoCallViewModel extends ViewModel {

    public MutableLiveData<Boolean> navigateToMainView = new MutableLiveData<>();
    public MutableLiveData<Long> countDowntime = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRegistered = new MutableLiveData<>();

    public MutableLiveData<Boolean> callDataSetupComplete = new MutableLiveData<>();
    public MutableLiveData<String> mediaChannelKey = new MutableLiveData<>();

    public MutableLiveData<Integer> callEvent = new MutableLiveData<>();
    public MutableLiveData<Integer> agoraAPIEvent = new MutableLiveData<>();
    public MutableLiveData<Integer> connectivityStatusChange = new MutableLiveData<>();

    public MutableLiveData<Boolean> videoStreamAvailable = new MutableLiveData<>();
    // public MutableLiveData<Boolean> userPausedCall = new MutableLiveData<>();

    private boolean isRemotePaused = false;
    public boolean isLocalPaused = false;

    public boolean isRemoteVideoViewRendered = false;

    public MutableLiveData<Boolean> isRemotePausedStateChanged = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLocalPausedStateChanged = new MutableLiveData<>();

    public Boolean remoteAudioMuted = false;
    public Boolean remoteVideoMuted = false;

    public int videoPeerId = 0;

    // GetMediaChannelKey getMediaChannelKey;
    GetThisDoctor getThisDoctor;

    SharedPreferences sp;

    public int uid;

    public IAgoraHelper agoraHelperBinding;

    public CallData callData;

    private final String TAG = "VideoCallViewModel";

    public RtcEngine m_agoraMedia;

    public VideoCallViewModel(GetThisDoctor getThisDoctor, SharedPreferences sp) {
        // this.getMediaChannelKey = getMediaChannelKey;
        this.getThisDoctor = getThisDoctor;
        this.sp = sp;
        callData = new CallData();
    }

    public void setUpCallData(String channelName, String patientUserId, String patientName, Boolean isResumed) {

        getThisDoctor.execute(new DisposableObserver<Doctor>() {
            @Override
            public void onNext(Doctor doctor) {

                if(isResumed) {
                    callData = CallDataHelper.getCallData(sp);
                } else {
                    callData.setDoctorId(doctor.getId());
                    callData.setDoctorName(doctor.getName());
                    callData.setDoctorPhone(doctor.getPhone());
                    callData.setDoctorImage(doctor.getImage());
                    callData.setChannelName(channelName);
                    callData.setPatientUserId(patientUserId);
                    callData.setPatientName(patientName);
                    callData.setState(new State());

                    CallDataHelper.saveCallData(callData, sp);
                }

                callDataSetupComplete.postValue(true);
            }

            @Override
            public void onError(Throwable e) {
                callDataSetupComplete.postValue(false);
            }

            @Override
            public void onComplete() {

            }
        }, null);
    }

    public void setAgoraHelperBinding(IAgoraHelper agoraHelperBinding) {

        this.agoraHelperBinding = agoraHelperBinding;

        // set API Callback for helper binding
        try {
            this.agoraHelperBinding.setAgoraAPICallback(apiCallback);
            m_agoraMedia = agoraHelperBinding.getRtcEngine();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        agoraAPIEvent.postValue(AGORA_RTC_INSTANCE_ACQUIRED);

        /*if(uid != 0) {
            Log.i(TAG, "call to getMediaChannel has been made uid has been set by service and is available by CallDataHelper");
            getMediaChannelKey(CallDataHelper.getCallData(sp).getChannelName(), String.valueOf(CallDataHelper.getCallData(sp).getUid()), String.valueOf(CallDataHelper.getCallData(sp).getPatientUserId()));
        } else {
            Log.e(TAG, "call to getMediaChannel not made as uid has not been set");
        }*/
    }

    public AgoraAPICallback getApiCallback () {
        return apiCallback;
    }

    public void toggleLocalAudioVideoStreams() {

        callData = CallDataHelper.getCallData(sp);

        boolean shouldPauseCall = (callData.getState().getVideoCallState() == VIDEO_CALL_STATE_PAUSED)? false : true;

        m_agoraMedia.muteLocalAudioStream(shouldPauseCall);
        m_agoraMedia.muteLocalVideoStream(shouldPauseCall);

        m_agoraMedia.muteAllRemoteAudioStreams(shouldPauseCall);
        m_agoraMedia.muteAllRemoteVideoStreams(shouldPauseCall);

        if(shouldPauseCall) {

            CallData callData = CallDataHelper.getCallData(sp);
            callData.getState().setVideoCallState(VIDEO_CALL_STATE_PAUSED);
            CallDataHelper.saveCallData(callData, sp);

            isRemoteVideoViewRendered = false;

            isLocalPaused = true;

            isLocalPausedStateChanged.postValue(true);

        } else {

            CallData callData = CallDataHelper.getCallData(sp);
            callData.getState().setVideoCallState(VIDEO_CALL_STATE_ON_GOING);
            CallDataHelper.saveCallData(callData, sp);

            isLocalPaused = false;

            isLocalPausedStateChanged.postValue(true);
        }
    }

    public boolean isRemotePaused() {

        return (remoteVideoMuted && remoteAudioMuted) ? true : false;
    }

    public boolean isLocalPaused() {
        return (CallDataHelper.getCallData(sp).getState().getVideoCallState() == VIDEO_CALL_STATE_PAUSED) ?  true: false;
    }


    /**
     * Define the callback stuff
     */
    AgoraAPICallback apiCallback = new AgoraAPICallback() {

        @Override
        public void onReconnecting(int var1) throws RemoteException {

        }

        @Override
        public void onReconnected(int var1) throws RemoteException {

        }

        @Override
        public void onLoginSuccess(int uid, int fd) throws RemoteException {

            VideoCallViewModel.this.uid = uid;

            agoraAPIEvent.postValue(AGORA_API_LOGIN_SUCCESS);
            // getMediaChannelKey(CallDataHelper.getCallData(sp).getChannelName(), String.valueOf(CallDataHelper.getCallData(sp).getUid()), String.valueOf(CallDataHelper.getCallData(sp).getPatientUserId()));
            Log.i(TAG, "onLoginSuccess called, login to Agora Signalling successfull");
        }

        @Override
        public void onLogout(int var1) throws RemoteException {

            VideoCallViewModel.this.uid = 0;
        }

        @Override
        public void onLoginFailed(int var1) throws RemoteException {
            agoraAPIEvent.postValue(AGORA_API_LOGIN_FAILED);
        }

        @Override
        public void onChannelJoined(String var1) throws RemoteException {

            // agoraHelperBinding.inviteUserToJoinChannel(CallDataHelper.getCallData(sp).getUserAccount());
            // setMessage("invite_sent");

            // CallStatusToneHelper.playCallStatusTone(m_agoraMedia, CallStatusToneHelper.CALL_CONNECTING);
            // log("Invite sent to - " + patientAccount);
        }

        @Override
        public void onChannelJoinFailed(String var1, int var2) throws RemoteException {

        }

        @Override
        public void onChannelLeaved(String var1, int var2) throws RemoteException {

        }

        @Override
        public void onChannelUserJoined(String var1, int var2) throws RemoteException {

        }

        @Override
        public void onChannelUserLeaved(String var1, int var2) throws RemoteException {

        }

        @Override
        public void onChannelUserList(List<String> var1, List<String> var2) throws RemoteException {

        }

        @Override
        public void onChannelQueryUserNumResult(String var1, int var2, int var3) throws RemoteException {

        }

        @Override
        public void onChannelAttrUpdated(String var1, String var2, String var3, String var4) throws RemoteException {

        }

        @Override
        public void onInviteReceived(String var1, String var2, int var3, String var4) throws RemoteException {

        }

        @Override
        public void onInviteReceivedByPeer(String var1, String var2, int var3) throws RemoteException {
            //Log.d(TAG, "Invite received by Peer: channelID - " + channelID + ", account - " + account + ", uid - " + uid);
            //log("Invite received by Peer: channelID - " + channelID + ", account - " + account + ", uid - " + uid);
            //setMessage("invite_received");
            callEvent.postValue(Constant.VIDEO_CALL_EVENT_INVITE_DELIVERED);
        }

        @Override
        public void onInviteAcceptedByPeer(String var1, String var2, int var3, String var4) throws RemoteException {
            // Log.d(TAG, "Invite Accepted by Peer: channelID - " + channelID + ", account - " + account + ", uid - " + uid + ", extra - " + extra);
            // log("Invite accepted by Peer: channelID - " + channelID + ", account - " + account + ", uid - " + uid);
            //sendDoctorInfo();

            // setMessage("invite_accepted");

            callEvent.postValue(Constant.VIDEO_CALL_EVENT_INVITE_ACCEPTED);
        }

        @Override
        public void onInviteRefusedByPeer(String var1, String var2, int var3, String var4) throws RemoteException {
            callEvent.postValue(Constant.VIDEO_CALL_EVENT_INVITE_REFUSED);
        }

        @Override
        public void onInviteFailed(String var1, String var2, int var3, int var4, String var5) throws RemoteException {
            callEvent.postValue(Constant.VIDEO_CALL_EVENT_INVITE_FAILED);
        }

        @Override
        public void onInviteEndByPeer(String var1, String var2, int var3, String var4) throws RemoteException {
            callEvent.postValue(Constant.VIDEO_CALL_EVENT_INVITE_ENDED_BY_PEER);
        }

        @Override
        public void onInviteEndByMyself(String var1, String var2, int var3) throws RemoteException {
            callEvent.postValue(Constant.VIDEO_CALL_EVENT_INVITE_ENDED_BY_MYSELF);
        }

        @Override
        public void onInviteMsg(String var1, String var2, int var3, String var4, String var5, String var6) throws RemoteException {

        }

        @Override
        public void onMessageSendError(String var1, int var2) throws RemoteException {

        }

        @Override
        public void onMessageSendProgress(String var1, String var2, String var3, String var4) throws RemoteException {

        }

        @Override
        public void onMessageSendSuccess(String var1) throws RemoteException {

        }

        @Override
        public void onMessageAppReceived(String var1) throws RemoteException {

        }

        @Override
        public void onMessageInstantReceive(String account, int uid, String msg) throws RemoteException {
            if(AgoraMessageHelper.getInfoMessage(msg).getMessageType().equals(VIDEO_MESSAGE_ON_ANOTHER_CALL)) {
                // show it on the status screen.
                // showUserBusyInfo();
            }
        }

        @Override
        public void onMessageChannelReceive(String var1, String var2, int var3, String var4) throws RemoteException {

        }

        @Override
        public void onLog(String var1) throws RemoteException {

        }

        @Override
        public void onInvokeRet(String var1, int var2, String var3, String var4) throws RemoteException {

        }

        @Override
        public void onMsg(String var1, String var2, String var3) throws RemoteException {

        }

        @Override
        public void onUserAttrResult(String var1, String var2, String var3) throws RemoteException {

        }

        @Override
        public void onUserAttrAllResult(String var1, String var2) throws RemoteException {

        }

        @Override
        public void onError(String var1, int var2, String var3) throws RemoteException {

        }

        @Override
        public void onQueryUserStatusResult(String var1, String var2) throws RemoteException {

        }

        @Override
        public void onDbg(String var1, String var2) throws RemoteException {

        }

        @Override
        public void onBCCall_result(String var1, String var2, String var3) throws RemoteException {

        }

        @Override
        public void joinOnGoingCall() throws RemoteException {

        }

        @Override
        public void onCallRingStarted() throws RemoteException {

        }

        @Override
        public void onCallDeclined() throws RemoteException {

        }

        @Override
        public void onCallAccepted() throws RemoteException {

        }

        @Override
        public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) throws RemoteException {
            if(videoPeerId != uid) {
                videoPeerId = uid;
                videoStreamAvailable.postValue(true);
            }
        }

        // When both of these events are tru then pause the call
        // Else if only Video is muted then show that user has paused audio
        @Override
        public void onUserMuteVideo(boolean isMuted) throws RemoteException {
            // User has muted video
            // userPausedCall.postValue(isMuted);
            remoteVideoMuted = isMuted;
            isRemotePausedStateChanged.postValue(isMuted);
        }

        @Override
        public void onUserMuteAudio(boolean isMuted) throws RemoteException {
            // User has muted audio
            // Pause entire call when audio is muted
            // isRemotePaused.postValue(true);
            remoteAudioMuted = isMuted;
            isRemotePausedStateChanged.postValue(isMuted);
        }

        @Override
        public void onRemoteVideoStats(int bitRate) throws RemoteException {
            Log.d(TAG, "onRemoteVideoStats called");
            videoStreamAvailable.postValue(true);
        }

        @Override
        public void onFirstLocalVideoFrame(int width, int height, int elapsed) {
            agoraAPIEvent.postValue(AGORA_FIRST_LOCAL_VIDEO_FRAME);
        }

        @Override
        public void onConnectivityStatusChange(int status) {
            connectivityStatusChange.postValue(status);
        }
    };
}