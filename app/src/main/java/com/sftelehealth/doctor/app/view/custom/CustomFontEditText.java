package com.sftelehealth.doctor.app.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.view.helper.FontCache;

/**
 * Created by rahul on 14/10/16.
 */
public class CustomFontEditText extends AppCompatEditText implements TextWatcher {

    private Typeface tf = null, tfhint = null;
    private String customhintfont, customFont;
    boolean inputtypepassword;
    private CharSequence chartype;

    // private CharSequence mSource;

    public CustomFontEditText(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        // initViews();
    }

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        // initViews();
        setCustomFontEdittext(context, attrs);

    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        // initViews();
        setCustomFontEdittext(context, attrs);
    }

    private void setCustomFontEdittext(Context ctx, AttributeSet attrs) {
        final TypedArray a = ctx.obtainStyledAttributes(attrs,
                R.styleable.CustomEditText);
        customFont = a.getString(R.styleable.CustomEditText_edittextfont);
        customhintfont = a
                .getString(R.styleable.CustomEditText_edittextfontHint);

        // custompwd = a.getString(R.styleable.CustomEditText_edittextpwd);
        inputtypepassword = a.getBoolean(
                R.styleable.CustomEditText_edittextpwd, false);
        setCustomFontEdittext(ctx, customFont);
        setCustomFontEdittextHint(ctx, customhintfont);

        chartype = (CharSequence) a
                .getText(R.styleable.CustomEditText_editcharpwd);
        setCustompwd(inputtypepassword);
        a.recycle();
    }

    public boolean setCustompwd(boolean pwd) {
        if (pwd) {
            this.setTransformationMethod(new PasswordCharacterChange());
        }
        return pwd;
    }

    public boolean setCustomFontEdittext(Context ctx, String asset) {
        try {
            tf = selectTypeface(ctx, Typeface.BOLD);
        } catch (Exception e) {
            return false;
        }
        setTypeface(tf);
        return true;
    }

    public boolean setCustomFontEdittextHint(Context ctx, String asset) {
        try {
            tfhint = selectTypeface(ctx, Typeface.NORMAL);
        } catch (Exception e) {
            return false;
        }
        setTypeface(tfhint);

        return true;
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        /*
        * information about the TextView textStyle:
        * http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
        */
        switch (textStyle) {
            case Typeface.BOLD: // bold
                return FontCache.getTypeface("Montserrat-Bold.ttf", context);

            case Typeface.ITALIC: // italic
                return FontCache.getTypeface("Montserrat-Light.ttf", context);

            case Typeface.BOLD_ITALIC: // bold italic
                return FontCache.getTypeface("Montserrat-Thin.ttf", context);

            case Typeface.NORMAL: // regular
            default:
                return FontCache.getTypeface("Montserrat-Regular.ttf", context);
        }
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore,
                              int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (text.length() < 0) {
            setCustomFontEdittextHint(getContext(), customhintfont);
        } else {
            setCustomFontEdittextHint(getContext(), customFont);
        }
        if (TextUtils.isEmpty(text)) {
            setCustomFontEdittextHint(getContext(), customhintfont);
        }
        // this.setText("*");
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    public class PasswordCharacterChange extends PasswordTransformationMethod {

        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            // TODO Auto-generated method stub
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }

            public char charAt(int index) {
                return chartype.charAt(0); // This is the important part
            }

            public int length() {
                return mSource.length(); // Return default
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }

}

