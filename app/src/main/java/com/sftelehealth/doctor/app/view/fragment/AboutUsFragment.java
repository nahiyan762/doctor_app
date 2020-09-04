package com.sftelehealth.doctor.app.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.view.activity.TermsAndConditionsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {

    TextView termsAndConditions;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    public static AboutUsFragment newInstance() {
        Bundle args = new Bundle();
        return new AboutUsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about_us, container, false);

        termsAndConditions = (TextView) v.findViewById(R.id.termsAndConditions);

        termsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent termsIntent = new Intent(getActivity(), TermsAndConditionsActivity.class);
                startActivity(termsIntent);
            }
        });

        return v;
    }

}
