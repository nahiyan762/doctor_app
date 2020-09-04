package com.sftelehealth.doctor.app.view.adapter.diff;

import androidx.recyclerview.widget.DiffUtil;

/**
 * Created by Rahul on 25/01/18.
 */

public class DiffCompletedConsults extends DiffUtil.Callback {

    @Override
    public int getOldListSize() {
        return 0;
    }

    @Override
    public int getNewListSize() {
        return 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
