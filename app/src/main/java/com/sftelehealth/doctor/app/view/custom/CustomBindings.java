package com.sftelehealth.doctor.app.view.custom;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.view.adapter.MedicineTypesSpinnerAdapter;
import com.sftelehealth.doctor.app.view.helper.CircleTransform;
import com.sftelehealth.doctor.domain.model.MedicineType;

/**
 * Created by Rahul on 07/01/18.
 */

public abstract class CustomBindings {

    @BindingAdapter(value = {"android:src", "imageType", "placeholder"}, requireAll = false)
    public static void setImageSrc(ImageView imageView, String src, String imageType, int placeholder) {

        RequestCreator requestCreator =  Picasso.with(imageView.getContext()).load(src);

        if(imageType != null && imageType.equalsIgnoreCase(imageView.getContext().getString(R.string.image_type_circular)))
            requestCreator.transform(new CircleTransform());

        if(placeholder != 0) {
            requestCreator.placeholder(placeholder);
        } else {
            requestCreator.placeholder(R.drawable.profile);
        }

        requestCreator.into(imageView);
    }

    @BindingAdapter({"android:textStyle"})
    public static void setTypeface(TextView v, String style) {
        switch (style) {
            case "bold":
                v.setTypeface(null, Typeface.BOLD);
                break;
            default:
                v.setTypeface(null, Typeface.NORMAL);
                break;
        }
    }

    @BindingAdapter(value = {"android:selectedItemPosition", "android:selectedItemPositionAttrChanged"}, requireAll = false)
    public static void bindSpinnerData(AppCompatSpinner spinner, MedicineType newSelectedValue, final InverseBindingListener newTextAttrChanged) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (newSelectedValue != null) {
            int pos = ((MedicineTypesSpinnerAdapter) spinner.getAdapter()).getPosition(newSelectedValue);
            spinner.setSelection(pos, true);
        }
    }

    //@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    @InverseBindingAdapter(attribute = "android:selectedItemPosition", event = "android:selectedItemPositionAttrChanged")
    public static int captureSelectedValue(AppCompatSpinner spinner) {
        return ((MedicineType) spinner.getSelectedItem()).getId();
    }

    @BindingAdapter(value = {"android:entries"})
    public static void bindSpinnerData(Spinner spinner, List<MedicineType> medicineTypes) {
        spinner.setAdapter(new MedicineTypesSpinnerAdapter(spinner.getContext(), R.layout.support_simple_spinner_dropdown_item, medicineTypes));
    }

}
