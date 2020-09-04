package com.sftelehealth.doctor.app.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.view.activity.CallbackDetailsActivity;
import com.sftelehealth.doctor.app.view.adapter.PriorCaseDocumentsAdapter;
import com.sftelehealth.doctor.app.view.helper.SpacesItemDecoration;
import com.sftelehealth.doctor.app.view.viewmodel.CallbackDetailsActivityFragmentViewModel;
import com.sftelehealth.doctor.databinding.FragmentPriorCaseDetailsBinding;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;


public class PriorCaseDetailsFragment extends Fragment implements PriorCaseDocumentsAdapter.PriorCaseDocumentsListListener{

    public static final String DOCUMENTS = "documents";
    private String docsString;

    CallbackDetailsActivityFragmentViewModel viewModel;

    FragmentPriorCaseDetailsBinding binding;


    public PriorCaseDetailsFragment() {
        // Required empty public constructor
    }

    public static PriorCaseDetailsFragment newInstance() {
        PriorCaseDetailsFragment fragment = new PriorCaseDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prior_case_details, container, false);

        viewModel = ((CallbackDetailsActivity)getActivity()).obtainViewModel(getActivity());

        PriorCaseDocumentsAdapter priorCaseDocumentsAdapter = new PriorCaseDocumentsAdapter(viewModel.callbackRequest.get().getDocs(), this);

        GridLayoutManager documentsLayoutManager = new GridLayoutManager(getContext(), 3);
        //documentsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        binding.documentsList.setLayoutManager(documentsLayoutManager);
        binding.documentsList.addItemDecoration(new SpacesItemDecoration(2, 4, 2, 4));
        binding.documentsList.setAdapter(priorCaseDocumentsAdapter);

        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDocumentImageClicked(String documentId) {
        ((CallbackDetailsActivity)getActivity()).showDocumentPreview(documentId);
    }
}
