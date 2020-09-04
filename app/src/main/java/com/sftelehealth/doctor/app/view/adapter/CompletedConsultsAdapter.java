package com.sftelehealth.doctor.app.view.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.view.adapter.listener.CompletedConsultsListener;
import com.sftelehealth.doctor.app.view.helper.CircleTransform;
import com.sftelehealth.doctor.app.view.helper.CloudinaryUrlHelper;
import com.sftelehealth.doctor.databinding.ItemActiveConsultBinding;
import com.sftelehealth.doctor.domain.model.Case;

/**
 * Created by Rahul on 27/12/17.
 * @see <a href="https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4">Android Data Binding: RecyclerView</a>
 */

public class CompletedConsultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Case> caseList;
    CompletedConsultsListener listener;

    public CompletedConsultsAdapter(List<Case> caseList, CompletedConsultsListener completedConsultsListener) {
        this.caseList = caseList;
        listener = completedConsultsListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemActiveConsultBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_active_consult, parent, false);
        return new CasesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BindingViewHolder)holder).bind(caseList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return caseList.size();
    }

    public class CasesViewHolder extends BindingViewHolder {

        public CasesViewHolder(ItemActiveConsultBinding binding) {
            super(binding);
        }

        @Override
        public void bind(Object obj, Object listener) {
            super.bind(obj, listener);

            Picasso.with(this.itemView.getContext())
                    .load(CloudinaryUrlHelper.getProfileUrl(((Case)obj).getPatientImage()))
                    .resize(100, 100)
                    .placeholder(R.drawable.profile)
                    .onlyScaleDown()
                    .transform(new CircleTransform())
                    .into(((ItemActiveConsultBinding)this.binding).profilePic);
        }
    }
}
