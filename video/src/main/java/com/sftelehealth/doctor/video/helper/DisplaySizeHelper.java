package com.sftelehealth.doctor.video.helper;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;

/**
 * Created by Rahul on 11/01/17.
 */

public class DisplaySizeHelper {

    /*public static int getDpSizeInPixel(int dps, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }*/

    public static Point getScreenSizeInPixel(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        /*int width = size.x;
        int height = size.y;*/
        return size;
    }

    public static int getDpSizeInPixel(float dp, Context context) {

        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );

        return px;
    }
}
