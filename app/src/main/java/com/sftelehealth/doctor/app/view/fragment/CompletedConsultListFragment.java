package com.sftelehealth.doctor.app.view.fragment;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.utils.Constant;
import com.sftelehealth.doctor.app.view.Navigator;
import com.sftelehealth.doctor.app.view.activity.BaseAppCompatActivity;
import com.sftelehealth.doctor.app.view.adapter.CompletedConsultsListAdapter;
import com.sftelehealth.doctor.app.view.adapter.listener.CompletedConsultsListener;
import com.sftelehealth.doctor.app.view.helper.SpacesItemDecoration;
import com.sftelehealth.doctor.app.view.viewmodel.CompletedConsultListFragmentViewModel;
import com.sftelehealth.doctor.databinding.FragmentCompletedConsultListBinding;
import com.sftelehealth.doctor.domain.model.Case;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedConsultListFragment extends Fragment implements CompletedConsultsListener {

    CompletedConsultListFragmentViewModel viewModel;

    // CompletedConsultsAdapter adapter;
    CompletedConsultsListAdapter adapter;

    FragmentCompletedConsultListBinding binding;

    Handler handler = new Handler();
    int page = 1;

    LinearLayoutManager layoutManager;

    public CompletedConsultListFragment() {
        // Required empty public constructor
    }

    public static CompletedConsultListFragment getInstance() {
        return new CompletedConsultListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_completed_consult_list, container, false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ((DashboardFragment) getParentFragment()).obtainCompletedConsultListViewModel();

        setUpObserver();
        setUpData();

        PackageInfo pInfo = null;
        try {
            pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        float appVersion = Float.valueOf(pInfo.versionName);


        // Updates doctor's device info and OneSignal ID
        viewModel.updateDoctor(Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID),
                ((BaseAppCompatActivity)getActivity()).getSharedPreferences().getString(Constant.ONESIGNAL_ID, ""),
                appVersion);

        viewModel.updateDoctorDetails();
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.getCases(page);
    }

    private void setUpObserver() {
        viewModel.notifyDataChange.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

                // if loaded successfully
                if (aBoolean) {
                    // hide the loading UI
                    binding.loadingContainer.setVisibility(View.GONE);

                    /*if (viewModel.caseList.get().size() > 0) {
                        binding.activeConsultsList.setVisibility(View.VISIBLE);

                        if (adapter == null) {
                            adapter = new CompletedConsultsAdapter(viewModel.caseList.get(), CompletedConsultListFragment.this);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            binding.activeConsultsList.setLayoutManager(layoutManager);
                            binding.activeConsultsList.addItemDecoration(new SpacesItemDecoration(8, 16, 8, 16));
                            binding.activeConsultsList.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        binding.noConsults.setVisibility(View.VISIBLE);
                    }*/

                    if(viewModel.tempCaseList.size() == 0 && page == 1) {
                        binding.noConsults.setVisibility(View.VISIBLE);
                    } else {

                        if(binding.noConsults.getVisibility() == View.VISIBLE)
                            binding.noConsults.setVisibility(View.GONE);

                        refreshCallbackList(viewModel.tempCaseList);
                    }
                }
            }
        });
    }

    private void setUpData() {

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.activeConsultsList.setLayoutManager(layoutManager);
        binding.activeConsultsList.addItemDecoration(new SpacesItemDecoration(8, 16, 8, 16));

        adapter = new CompletedConsultsListAdapter(viewModel.caseList.get(), CompletedConsultListFragment.this, binding.activeConsultsList);
        binding.activeConsultsList.setAdapter(adapter);
    }

    private void refreshCallbackList(List<Case> tempCases) {

        adapter.setLoaded();
        if (page > 1) {
            viewModel.caseList.get().remove(viewModel.caseList.get().size() - 1);
            adapter.notifyItemRemoved(viewModel.caseList.get().size());
        } else {
            viewModel.caseList.get().clear();
            adapter.notifyDataSetChanged();
        }

        if (tempCases != null && tempCases.size() > 0) {
            //page = page + 1;
            viewModel.caseList.get().addAll(tempCases);

            if (viewModel.caseList.get().size() > 0) {
                // Callback requests available hide the textview
                binding.noConsults.setVisibility(View.INVISIBLE);
                binding.activeConsultsList.setVisibility(View.VISIBLE);
            } else {
                // No Callback requests. Hide the recycler view
                binding.activeConsultsList.setVisibility(View.INVISIBLE);
                binding.noConsults.setVisibility(View.VISIBLE);
            }

            if (page == 1) {
                // if first page was loaded
                adapter.notifyDataSetChanged();
            } else {
                // if a subsequent page was loaded

                // find total count and current visible count
                int totalItemsCount = layoutManager.getItemCount();
                int visibleItemsCount = layoutManager.getChildCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                int currentItemPosition = visibleItemsCount + pastVisibleItems;

                if (totalItemsCount != 0) {
                    adapter.notifyItemChanged(totalItemsCount - 1);
                    adapter.notifyItemRangeInserted(totalItemsCount, totalItemsCount + tempCases.size() - 1);
                }

                //blogsRecyclerView.setAdapter(blogsAdapter);
                if (binding.activeConsultsList != null && currentItemPosition > 0) {
                    binding.activeConsultsList.smoothScrollToPosition(currentItemPosition - 1);
                }
            }
        } else {
            // no more posts to load, show a message
            if (page > 1)
                Snackbar.make(binding.getRoot(), "No more requests!", Snackbar.LENGTH_SHORT).show();
            page--;
        }

        if (page == 1)
            setOnLoadMoreListeners();
    }

    private void setOnLoadMoreListeners() {
        adapter.setOnLoadMoreListener(new CompletedConsultsListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                page = page + 1;
                viewModel.caseList.get().add(null);
                adapter.notifyItemInserted(viewModel.caseList.get().size() - 1);

                // make a call with current page number
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity() != null)
                            viewModel.getCases(page);
                        //new RetroDocAppointments(FragmentCallRequests.this, mContainer, getActivity(), String.valueOf(page));
                    }
                }, 3000);
                //new RetroDocAppointments(FragmentCallRequests.this, mContainer, getActivity(), String.valueOf(page));
            }

            @Override
            public void loadFirst() {
                    /*mAppointments.getRequests().clear();
                    new RetroDocAppointments(FragmentCallRequests.this, mContainer, getActivity(), "1");
                    page = 1;*/
            }
        });
    }

    @Override
    public void onCaseClicked(Case caseItem) {
        Navigator.navigateToCaseDetails(getContext(), caseItem.getCaseId());
    }
}
