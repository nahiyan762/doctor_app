package com.sftelehealth.doctor.video.service;

import android.util.Log;

import io.agora.rtc.IRtcEngineEventHandler;

public class RtcEngineEventHandler extends IRtcEngineEventHandler {

    RtcEngineEventListener listener;
    private final String TAG = "RtcEngineEventHandler";

    public RtcEngineEventHandler(RtcEngineEventListener listener) {

        this.listener = listener;
    }

    @Override
    public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
        super.onFirstRemoteVideoDecoded(uid, width, height, elapsed);

        listener.onFirstRemoteVideoDecoded(uid, width, height, elapsed);
    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
        super.onJoinChannelSuccess(channel, uid, elapsed);
        Log.d(TAG, "onJoinChannelSuccess");
    }

    @Override
    public void onError(int err) {
        super.onError(err);
        Log.d(TAG, "onError - " + err);
    }

    @Override
    public void onVideoStopped() {
        super.onVideoStopped();

        Log.i(TAG, "onVideoStopped");
    }

    @Override
    public void onCameraReady() {
        super.onCameraReady();

        Log.i(TAG, "onCameraReady");
    }

    @Override
    public void onConnectionLost() {
        super.onConnectionLost();

        Log.i(TAG, "onConnectionLost");
    }

    @Override
    public void onFirstLocalVideoFrame(int width, int height, int elapsed) {
        super.onFirstLocalVideoFrame(width, height, elapsed);

        Log.i(TAG, "onFirstLocalVideoFrame");
        listener.onFirstLocalVideoFrame(width, height, elapsed);
    }

    @Override
    public void onFirstRemoteVideoFrame(int uid, int width, int height, int elapsed) {
        super.onFirstRemoteVideoFrame(uid, width, height, elapsed);

        Log.i(TAG, "onFirstRemoteViewFrame");
    }

    @Override
    public void onUserEnableVideo(int uid, boolean enabled) {
        super.onUserEnableVideo(uid, enabled);

        Log.i(TAG, "onUserEnableVideo");
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {
        super.onUserJoined(uid, elapsed);

        Log.i(TAG, "onUserEnableVideo");
    }

    @Override
    public void onUserMuteAudio(int uid, boolean muted) {
        super.onUserMuteAudio(uid, muted);

        listener.onUserMuteAudio(muted);
    }

    @Override
    public void onUserMuteVideo(int uid, boolean muted) {
        super.onUserMuteVideo(uid, muted);

        listener.onUserMuteVideo(muted);
    }

    @Override
    public void onRemoteVideoStats(RemoteVideoStats stats) {
        super.onRemoteVideoStats(stats);

        Log.i(TAG, "onRemoteVideoStats: bitrate - " + stats.receivedBitrate);

        listener.onRemoteVideoStats(stats.receivedBitrate);
    }

    @Override
    public void onLocalVideoStats(LocalVideoStats stats) {
        super.onLocalVideoStats(stats);

        Log.i(TAG, "onLocalVideoStats");
    }

    @Override
    public void onRtcStats(RtcStats stats) {
        super.onRtcStats(stats);

        Log.i(TAG, "onRtcStats");
    }

    @Override
    public void onAudioVolumeIndication(AudioVolumeInfo[] speakers, int totalVolume) {
        super.onAudioVolumeIndication(speakers, totalVolume);

        Log.i(TAG, "onAudioVolumeIndication");
    }

    public interface RtcEngineEventListener {
        void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed);
        void onFirstLocalVideoFrame(int width, int height, int elapsed);
        void onUserMuteAudio(boolean isMuted);
        void onUserMuteVideo(boolean isMuted);
        void onRemoteVideoStats(int bitRate);
    }
}
