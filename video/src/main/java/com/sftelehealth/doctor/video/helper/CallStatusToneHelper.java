package com.sftelehealth.doctor.video.helper;

import io.agora.rtc.RtcEngine;

public class CallStatusToneHelper {

    public static String CALL_CONNECTING = "call_connecting";
    public static String CALL_RINGING = "call_ringing";
    public static String CALL_REJECTED = "call_rejected";

    public static void playCallStatusTone(RtcEngine rtcEngine, String toneType) {

        String filePath = "";
        boolean loopback = true;
        boolean replace = false;
        int cycle = -1;

        rtcEngine.stopAudioMixing();

        if(toneType.equalsIgnoreCase(CALL_CONNECTING)) {

            filePath = "/assets/connecting.mp3";

        } else if(toneType.equalsIgnoreCase(CALL_RINGING)) {

            filePath = "/assets/phone_ringing.mp3";

        } else if(toneType.equalsIgnoreCase(CALL_REJECTED)) {

            cycle = 1;
            filePath = "/assets/user_busy_once.mp3";
        }
        rtcEngine.startAudioMixing(filePath, loopback, replace, cycle);
    }

    public static void stopCallStatusTone(RtcEngine rtcEngine) {
        rtcEngine.stopAudioMixing();
    }

}
