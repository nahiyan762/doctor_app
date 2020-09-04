package com.sftelehealth.doctor.app.view.adapter;

import androidx.paging.PagedListAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.view.adapter.listener.CallbackRequestListener;
import com.sftelehealth.doctor.app.view.helper.CircleTransform;
import com.sftelehealth.doctor.app.view.helper.CloudinaryUrlHelper;
import com.sftelehealth.doctor.databinding.ItemCallbackRequestBinding;
import com.sftelehealth.doctor.domain.model.CallbackRequest;

import java.util.List;

/**
 * Created by Rahul on 29/01/18.
 */

public class CallbackRequestPageAdapter extends PagedListAdapter<CallbackRequest, RecyclerView.ViewHolder> {

    List<CallbackRequest> callbackRequestList;
    CallbackRequestListener listener;

    public CallbackRequestPageAdapter(List<CallbackRequest> callbackRequestList, CallbackRequestListener callbackRequestListener) {
        super(diffCallback);
        this.callbackRequestList = callbackRequestList;
        listener = callbackRequestListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCallbackRequestBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_callback_request, parent, false);
        return new CallbackRequestViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BindingViewHolder)holder).bind(callbackRequestList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return callbackRequestList.size();
    }

    public static final DiffUtil.ItemCallback diffCallback = new DiffUtil.ItemCallback<CallbackRequest>() {

        @Override
        public boolean areItemsTheSame(@NonNull CallbackRequest oldItem, @NonNull CallbackRequest newItem) {
            return (oldItem.getCallbackId() == newItem.getCallbackId()) ? true : false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CallbackRequest oldItem, @NonNull CallbackRequest newItem) {
            return (oldItem.getStatus().equalsIgnoreCase(newItem.getStatus())) ? true : false;
        }
    };

    public class CallbackRequestViewHolder extends BindingViewHolder {

        public CallbackRequestViewHolder(ItemCallbackRequestBinding binding) {
            super(binding);
        }

        @Override
        public void bind(Object obj, Object listener) {
            super.bind(obj, listener);

            Picasso.with(this.itemView.getContext())
                    .load(CloudinaryUrlHelper.getProfileUrl(((CallbackRequest)obj).getPatientImage()))
                    .resize(100, 100)
                    .placeholder(R.drawable.profile)
                    .onlyScaleDown()
                    .transform(new CircleTransform())
                    .into(((ItemCallbackRequestBinding)this.binding).profilePic);
        }
    }
}
