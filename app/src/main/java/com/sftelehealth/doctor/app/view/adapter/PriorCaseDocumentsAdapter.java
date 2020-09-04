package com.sftelehealth.doctor.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.databinding.ItemPriorCaseDocumentBinding;
import com.sftelehealth.doctor.domain.model.Document;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class PriorCaseDocumentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<Document> documents;
    Context context;

    PriorCaseDocumentsListListener listener;

    public PriorCaseDocumentsAdapter (ArrayList<Document> documents, PriorCaseDocumentsListListener listener) {
        this.documents = documents;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null)
            context = parent.getContext();

        ItemPriorCaseDocumentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_prior_case_document, parent, false);
        return new PriorCaseDocumentsAdapter.DocumentsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BindingViewHolder)holder).bind(documents.get(position), listener);

        String url = documents.get(position).getPreviewUrl();
        //String url = documents.get(position).getDocumentType() == Document.IMAGE ? documents.get(position).getUrl() : documents.get(position).getPreviewUrl();

        Picasso.with(context)
                .load(url)
                //.resize(ImageUtils.getSpecifiedDimension(, 100, true), 0)
                //.resize(ImageUtils.getSpecifiedDimension())
                .fit()
                .centerInside()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(((ItemPriorCaseDocumentBinding)(((BindingViewHolder) holder).binding)).documentThumbnail);
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    public class DocumentsViewHolder extends BindingViewHolder {

        public DocumentsViewHolder(ViewDataBinding binding) {
            super(binding);
        }
    }

    public interface PriorCaseDocumentsListListener {
        void onDocumentImageClicked(String documentId);
    }
}
