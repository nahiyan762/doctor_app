package com.sftelehealth.doctor.video.agora.message;

import com.google.gson.Gson;
import com.sftelehealth.doctor.video.models.InfoMessage;

/**
 * Created by Rahul on 15/07/17.
 */

public class AgoraMessageHelper {

    private static Gson gson = new Gson();

    public static String sendMessage(InfoMessage message) {


        return gson.toJson(message);
    }

    public static InfoMessage getInfoMessage(String message) {
        return gson.fromJson(message, InfoMessage.class);
    }

}
