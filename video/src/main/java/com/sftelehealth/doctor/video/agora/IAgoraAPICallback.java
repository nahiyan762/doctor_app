package com.sftelehealth.doctor.video.agora;

import android.os.RemoteException;

import java.util.List;

public interface IAgoraAPICallback {

    void onReconnecting(int var1) throws RemoteException;

    void onReconnected(int var1) throws RemoteException;

    void onLoginSuccess(int var1, int var2) throws RemoteException;

    void onLogout(int var1) throws RemoteException;

    void onLoginFailed(int var1) throws RemoteException;

    void onChannelJoined(String var1) throws RemoteException;

    void onChannelJoinFailed(String var1, int var2) throws RemoteException;

    void onChannelLeaved(String var1, int var2) throws RemoteException;

    void onChannelUserJoined(String var1, int var2) throws RemoteException;

    void onChannelUserLeaved(String var1, int var2) throws RemoteException;

    void onChannelUserList(List<String> var1, List<String> var2) throws RemoteException;

    void onChannelQueryUserNumResult(String var1, int var2, int var3) throws RemoteException;

    void onChannelAttrUpdated(String var1, String var2, String var3, String var4) throws RemoteException;

    void onInviteReceived(String var1, String var2, int var3, String var4) throws RemoteException;

    void onInviteReceivedByPeer(String var1, String var2, int var3) throws RemoteException;

    void onInviteAcceptedByPeer(String var1, String var2, int var3, String var4) throws RemoteException;

    void onInviteRefusedByPeer(String var1, String var2, int var3, String var4) throws RemoteException;

    void onInviteFailed(String var1, String var2, int var3, int var4, String var5) throws RemoteException;

    void onInviteEndByPeer(String var1, String var2, int var3, String var4) throws RemoteException;

    void onInviteEndByMyself(String var1, String var2, int var3) throws RemoteException;

    void onInviteMsg(String var1, String var2, int var3, String var4, String var5, String var6) throws RemoteException;

    void onMessageSendError(String var1, int var2) throws RemoteException;

    void onMessageSendProgress(String var1, String var2, String var3, String var4) throws RemoteException;

    void onMessageSendSuccess(String var1) throws RemoteException;

    void onMessageAppReceived(String var1) throws RemoteException;

    void onMessageInstantReceive(String var1, int var2, String var3) throws RemoteException;

    void onMessageChannelReceive(String var1, String var2, int var3, String var4) throws RemoteException;

    void onLog(String var1) throws RemoteException;

    void onInvokeRet(String var1, int var2, String var3, String var4) throws RemoteException;

    void onMsg(String var1, String var2, String var3) throws RemoteException;

    void onUserAttrResult(String var1, String var2, String var3) throws RemoteException;

    void onUserAttrAllResult(String var1, String var2) throws RemoteException;

    void onError(String var1, int var2, String var3) throws RemoteException;

    void onQueryUserStatusResult(String var1, String var2) throws RemoteException;

    void onDbg(String var1, String var2) throws RemoteException;

    void onBCCall_result(String var1, String var2, String var3) throws RemoteException;

    void joinOnGoingCall() throws RemoteException;

    void onCallRingStarted() throws RemoteException;

    void onCallDeclined() throws RemoteException;

    void onCallAccepted() throws RemoteException;

    void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) throws RemoteException;

    void onUserMuteVideo(boolean isMuted) throws RemoteException;

    void onUserMuteAudio(boolean isMuted) throws RemoteException;

    void onRemoteVideoStats(int bitRate) throws RemoteException;

    void onConnectivityStatusChange(int status);
    
    void onFirstLocalVideoFrame(int width, int height, int elapsed) throws RemoteException;
}
