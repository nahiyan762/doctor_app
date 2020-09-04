package com.sftelehealth.doctor.video.agora;

import android.os.RemoteException;

import io.agora.rtc.RtcEngine;


public interface IAgoraHelper {

    void setAgoraAPICallback(IAgoraAPICallback callback) throws RemoteException;

    void getAgoraAPI() throws RemoteException;

    void getChannelID() throws RemoteException;

    void login(String account) throws RemoteException;

    void logout() throws RemoteException;

    void joinChannel(String channelName) throws RemoteException;

    void joinAPIChannel() throws RemoteException;

    void joinMediaChannel(String mediaChannelKey) throws RemoteException;

    void leaveAPIChannel() throws RemoteException;

    void messageChannelSend(String message) throws RemoteException;

    void messageInstantSend(String account, String msg) throws RemoteException;

    void acceptInvite(String channelID, String account, int uid) throws RemoteException;

    void declineInvite(String channelID, String account, int uid) throws RemoteException;

    void inviteUserToJoinChannel(String patientAccount) throws RemoteException;

    void endInvite() throws RemoteException;

    void setupRemoteView(int uid) throws RemoteException;

    void getUID() throws RemoteException;

    int getCallState() throws RemoteException;

    void setCallState(int callState) throws RemoteException;

    void setUID(int my_uid) throws RemoteException;

    void set_state_logout() throws RemoteException;

    void set_state_login() throws RemoteException;

    void set_state_incall() throws RemoteException;

    void set_state_notincall() throws RemoteException;

    RtcEngine getRtcEngine() throws RemoteException;

    void callStatusChanged();
}
