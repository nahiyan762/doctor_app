package com.sftelehealth.doctor.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.databinding.ItemDocumentImageBinding;
import com.sftelehealth.doctor.domain.model.Document;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Rahul on 09/01/18.
 */

public class DocumentsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    List<Document> documents;
    DocumentsListListener listener;
    Context context;

    public DocumentsListAdapter(List<Document> documents, DocumentsListListener listener) {
        this.documents = documents;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(context == null)
            context = parent.getContext();

        ItemDocumentImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_document_image, parent, false);
        return new DocumentsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BindingViewHolder)holder).bind(documents.get(position), listener);

        String url = documents.get(position).getPreviewUrl();
        //String url = documents.get(position).getDocumentType() == Document.IMAGE ? documents.get(position).getUrl() : documents.get(position).getPreviewUrl();

        Picasso.with(context)
                .load(url)
                //.resize(ImageUtils.getSpecifiedDimension(, 100, true), 0)
                //.resize(ImageUtils.getSpecifiedDimension())
                .fit()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(((ItemDocumentImageBinding)(((BindingViewHolder) holder).binding)).documentThumbnail);
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

    public interface DocumentsListListener {
        void onDocumentImageClicked(String documentId);
    }
}
