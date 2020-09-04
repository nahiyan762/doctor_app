package com.sftelehealth.doctor.app.utils;

import android.app.Activity;
import android.view.Display;

public class ImageUtils {

    public static Activity context;
    //static DlgTransparentProgress mProgress;
    static Display display = null;

    /*public static int getSpecifiedDimension(Context activity, int percentage, boolean isWidth) {

        int required_dim = 0;
        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();;
        if(display == null)
            display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int actual_dim = (isWidth)?size.x:size.y ;
        required_dim = ((percentage * actual_dim) / 100);
        return required_dim;
    }

    public static int getImageResizeWidth(int imageHeight) {

    }*/
}