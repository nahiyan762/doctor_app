package com.sftelehealth.doctor.video.agora;

import android.os.RemoteException;

import java.util.List;

public class AgoraAPICallback implements IAgoraAPICallback {



    @Override
    public void onReconnecting(int var1) throws RemoteException {

    }

    @Override
    public void onReconnected(int var1) throws RemoteException {

    }

    @Override
    public void onLoginSuccess(int var1, int var2) throws RemoteException {

    }

    @Override
    public void onLogout(int var1) throws RemoteException {

    }

    @Override
    public void onLoginFailed(int var1) throws RemoteException {

    }

    @Override
    public void onChannelJoined(String var1) throws RemoteException {

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

    }

    @Override
    public void onInviteAcceptedByPeer(String var1, String var2, int var3, String var4) throws RemoteException {

    }

    @Override
    public void onInviteRefusedByPeer(String var1, String var2, int var3, String var4) throws RemoteException {

    }

    @Override
    public void onInviteFailed(String var1, String var2, int var3, int var4, String var5) throws RemoteException {

    }

    @Override
    public void onInviteEndByPeer(String var1, String var2, int var3, String var4) throws RemoteException {

    }

    @Override
    public void onInviteEndByMyself(String var1, String var2, int var3) throws RemoteException {

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
    public void onMessageInstantReceive(String var1, int var2, String var3) throws RemoteException {

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

    }

    @Override
    public void onUserMuteVideo(boolean isMuted) throws RemoteException {

    }

    @Override
    public void onUserMuteAudio(boolean isMuted) throws RemoteException {

    }

    @Override
    public void onRemoteVideoStats(int bitRate) throws RemoteException {

    }

    @Override
    public void onConnectivityStatusChange(int status) {

    }

    @Override
    public void onFirstLocalVideoFrame(int width, int height, int elapsed) throws RemoteException {

    }
}
