package com.sftelehealth.doctor.app.view.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.domain.model.MedicineType;

/**
 * Created by Rahul on 12/02/18.
 */

public class MedicineTypesSpinnerAdapter extends ArrayAdapter<MedicineType> {

    Context context;
    List<MedicineType> values;

    public MedicineTypesSpinnerAdapter(@NonNull Context context, int resource, List<MedicineType> medicineTypes) {
        super(context, resource);
        this.context = context;
        this.values = medicineTypes;
    }

    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public MedicineType getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values.get(position).getType());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.support_simple_spinner_dropdown_item, parent, false);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getType());
        return label;
    }
}
