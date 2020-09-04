package com.sftelehealth.doctor.app.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.internal.di.components.UseCaseComponent;
import com.sftelehealth.doctor.app.view.activity.MainActivity;
import com.sftelehealth.doctor.app.view.adapter.DashboardPagerAdapter;
import com.sftelehealth.doctor.app.view.custom.SlidingTabLayout;
import com.sftelehealth.doctor.app.view.viewmodel.CallbackListFragmentViewModel;
import com.sftelehealth.doctor.app.view.viewmodel.CompletedConsultListFragmentViewModel;
import com.sftelehealth.doctor.app.view.viewmodel.ViewModelFactory;
import com.sftelehealth.doctor.databinding.FragmentDashboardBinding;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    UseCaseComponent useCaseComponent;
    FragmentDashboardBinding binding;

    DashboardPagerAdapter pagerAdapter;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_dashboard, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // initialize injector and get the use case component
        useCaseComponent = ((MainActivity)getActivity()).getUseCaseComponent();

        initializeView();
    }

    public CallbackListFragmentViewModel obtainCallbackListViewModel() {

        ViewModelFactory factory = new ViewModelFactory(useCaseComponent);
        return ViewModelProviders.of(this, factory).get(CallbackListFragmentViewModel.class);   //new CallbackListFragmentViewModel();
    }

    public CompletedConsultListFragmentViewModel obtainCompletedConsultListViewModel() {

        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = new ViewModelFactory(useCaseComponent);

        return ViewModelProviders.of(this, factory).get(CompletedConsultListFragmentViewModel.class);
    }

    private void initializeView() {
        //viewPager = (ViewPager) findViewById(R.id.pager);

        pagerAdapter = new DashboardPagerAdapter(getChildFragmentManager(), getContext());

        //slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        binding.slidingTabs.setCustomTabView(R.layout.tab_indicator_2, android.R.id.text1);
        binding.slidingTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getContext(), R.color.white);

                /*if(Build.VERSION.SDK_INT > 22)
                    return getActivity().getColor(R.color.colorPrimary);
                else
                    return getActivity().getResources().getColor(R.color.colorPrimary, getActivity().getTheme());*/
            }

            @Override
            public int getDividerColor(int position) {
                return ContextCompat.getColor(getContext(), android.R.color.transparent);
                /*if(Build.VERSION.SDK_INT > 22)
                    return getActivity().getColor(android.R.color.transparent);
                else
                    return getActivity().getResources().getColor(android.R.color.transparent, getActivity().getTheme());*/
            }
        });

        binding.pager.setAdapter(pagerAdapter);

        // setup the view pager indicator
        binding.slidingTabs.setViewPager(binding.pager);

        if(getActivity().getIntent().hasExtra("callbackId"))
            binding.pager.setCurrentItem(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity().getIntent().hasExtra("callbackId"))
            binding.pager.setCurrentItem(1);
    }
}
