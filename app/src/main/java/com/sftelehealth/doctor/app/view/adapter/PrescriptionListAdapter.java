package com.sftelehealth.doctor.app.view.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.databinding.ItemPrescriptionListBinding;
import com.sftelehealth.doctor.domain.model.Prescription;

/**
 * Created by Rahul on 09/01/18.
 */

public class PrescriptionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Prescription> prescriptions;
    PrescriptionListListener listener;

    public PrescriptionListAdapter(List<Prescription> prescriptions, PrescriptionListListener listener) {
        this.prescriptions = prescriptions;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPrescriptionListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_prescription_list, parent, false);
        return new PrescriptionHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BindingViewHolder)holder).bind(prescriptions.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }

    public class PrescriptionHolder extends BindingViewHolder {

        public PrescriptionHolder(ViewDataBinding binding) {
            super(binding);
        }
    }

    public interface PrescriptionListListener{
        void onPrescriptionClicked(Prescription prescription);
    }
}
