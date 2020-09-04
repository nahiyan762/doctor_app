package com.sftelehealth.doctor.app.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.view.helper.FontCache;

/**
 * Created by Rahul on 02/02/17.
 */

public class CustomButton extends AppCompatButton {

    String customFontName;

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        TypedArray typedArrayAttrs = context.obtainStyledAttributes(attrs,
                R.styleable.CustomFontTextView);

        // always set it to bold
        int cf = 2;
        //int cf = typedArrayAttrs.getInteger(R.styleable.CustomFontTextView_fontName, 0);

        setTypeface(getTypeFace(context, cf));

        typedArrayAttrs.recycle();
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        /*
        * information about the TextView textStyle:
        * http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
        */
        switch (textStyle) {
            case Typeface.BOLD: // bold
                Log.d("CustomFont", "BOLD - Montserrat-Bold.ttf");
                return FontCache.getTypeface("Montserrat-Bold.ttf", context);

            case Typeface.ITALIC: // italic
                Log.d("CustomFont", "ITALIC - Montserrat-Light.ttf");
                return FontCache.getTypeface("Montserrat-Light.ttf", context);

            case Typeface.BOLD_ITALIC: // bold italic
                Log.d("CustomFont", "BOLD_ITALIC - Montserrat-Thin.ttf");
                return FontCache.getTypeface("Montserrat-Thin.ttf", context);

            case Typeface.NORMAL: // regular
            default:
                // Log.d("CustomFont", "DEFAULT - Montserrat-Regular.ttf");
                return FontCache.getTypeface("Montserrat-Regular.ttf", context);
        }
    }

    private Typeface getTypeFace(Context context, int cf) {

        int fontName = 0;
        switch (cf)
        {
            case 1:
                fontName = R.string.black;
                break;
            case 2:
                fontName = R.string.bold;
                break;
            case 3:
                fontName = R.string.light;
                break;
            case 4:
                fontName = R.string.regular;
                break;
            case 5:
                fontName = R.string.thin;
                break;
            default:
                fontName = R.string.regular;
                break;
        }

        customFontName = getResources().getString(fontName);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                customFontName);
        //setTypeface(tf);

        return tf;
    }
}