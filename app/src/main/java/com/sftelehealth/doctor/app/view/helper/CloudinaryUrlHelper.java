package com.sftelehealth.doctor.app.view.helper;

import android.content.Context;
import androidx.annotation.NonNull;


/**
 * Created by Rahul on 27/01/17.
 */

public class CloudinaryUrlHelper {

    /**
     * DPR_AUTO --> Auto DPI adjustment
     * Q_AUTO --> Auto quality
     * F_AUTO --> Auto format the image to best possible deliverable format.
     * You can use JPG also here if you want shortest possible image with no transparency.
     * Accordingly adjust Q_AUTO to Q_70 something to adjust the quality.
     * C_FILL -->
     * G_FACE -->
     * W_100 -->
     * H_100 -->
     * @param url
     * @return
     */
    // @org.jetbrains.annotations.NotNull
    // @Contract(pure = true)
    public static String getProfileUrl(String url) {
        String profileParams = "c_fill,g_face,dpr_auto,q_auto,f_auto";
        return getFinalUrl(profileParams.concat(getCloudinaryUrl(url)));
    }

    // @org.jetbrains.annotations.NotNull
    // @Contract(pure = true)
    public static String getBasicUrl(String url) {
        String basicParams = "dpr_auto,q_auto,f_auto";
        return getFinalUrl(basicParams.concat(getCloudinaryUrl(url)));
    }

    @NonNull
    // @Contract(pure = true)
    public static String getDoctorMediumUrl(String url, int width, int height) {
        String docMediumParams = "w_" + width + ",h_" + height + ",c_fill,g_face,dpr_auto,q_auto,f_auto";
        String finalUrl = getFinalUrl(docMediumParams.concat(getCloudinaryUrl(url)));
        // Timber.d("Doctor List Image: %s", finalUrl);
        return finalUrl;
    }

    @NonNull
    public static String getDoctorBackDropUrl(String url, Context context, int height, int width) {
        DisplaySizeHelper.getDpSizeInPixel(height, context);
        String docBackDropParams = "w_" + width + ",h_" + height + ",c_fill,g_face,dpr_auto,q_auto,f_auto";
        String finalUrl = getFinalUrl(docBackDropParams.concat(getCloudinaryUrl(url)));
        // Timber.d("Backdrop Image: %s", finalUrl);
        return finalUrl;
    }

    /*
    REPLACE https://s3.ap-south-1.amazonaws.com with s3_uploads
    REPLACE http://www.doctor24x7.in with migrated_images
     */
    private static String getCloudinaryUrl(String url) {

        if(url != null) {
            if (url.contains("https://s3.ap-south-1.amazonaws.com"))
                return url.replace("https://s3.ap-south-1.amazonaws.com", "/s3_uploads");
            else if (url.contains("http://www.doctor24x7.in"))
                return url.replace("http://www.doctor24x7.in", "/migrated_images");
            else
                return "/" + url;
        } else {
            return "";
        }
    }

    @NonNull
    private static String getFinalUrl(String url) {
        if(url.contains("https://s3-ap-southeast-1.amazonaws.com"))
            return "http://res.cloudinary.com/traktion-solutions-pvt-ltd/image/fetch/".concat(url);
        else
            return "http://res.cloudinary.com/traktion-solutions-pvt-ltd/image/upload/".concat(url);
    }
}