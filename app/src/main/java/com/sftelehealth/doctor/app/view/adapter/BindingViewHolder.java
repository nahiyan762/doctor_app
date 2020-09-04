package com.sftelehealth.doctor.app.view.adapter;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.sftelehealth.doctor.BR;
/**
 * Created by Rahul on 27/12/17.
 */

public class BindingViewHolder extends RecyclerView.ViewHolder {

    protected final ViewDataBinding binding;

    public BindingViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object obj) {
        binding.setVariable(BR.item, obj);
        binding.executePendingBindings();
    }

    public void bind(Object obj, Object listener) {
        binding.setVariable(BR.listener, listener);
        bind(obj);
    }
}
