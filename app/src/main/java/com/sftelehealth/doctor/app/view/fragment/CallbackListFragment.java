package com.sftelehealth.doctor.app.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.utils.ContactsProvider;
import com.sftelehealth.doctor.app.utils.PermissionsHelper;
import com.sftelehealth.doctor.app.view.Navigator;
import com.sftelehealth.doctor.app.view.adapter.CallbackRequestListAdapter;
import com.sftelehealth.doctor.app.view.adapter.listener.CallbackRequestListener;
import com.sftelehealth.doctor.app.view.helper.SpacesItemDecoration;
import com.sftelehealth.doctor.app.view.viewmodel.CallbackListFragmentViewModel;
import com.sftelehealth.doctor.databinding.FragmentCallbackListBinding;
import com.sftelehealth.doctor.domain.model.CallbackRequest;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallbackListFragment extends BaseFragment implements CallbackRequestListener {

    CallbackListFragmentViewModel viewModel;

    FragmentCallbackListBinding binding;

    CallbackRequestListAdapter adapter;
    // CallbackRequestPageAdapter adapter;
    CompositeDisposable disposable;

    BroadcastReceiver callRequestsReceiver;
    Handler handler = new Handler();
    int page = 1;

    LinearLayoutManager layoutManager;

    public CallbackListFragment() {
        // Required empty public constructor
        disposable = new CompositeDisposable();
    }

    public static CallbackListFragment getInstance() {
        return new CallbackListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_callback_list, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ((DashboardFragment) getParentFragment()).obtainCallbackListViewModel();

        setUpObserver();
        setUpData();
    }

    @Override
    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(callRequestsReceiver,
                new IntentFilter("refresh_event"));

        //refreshCallbackList();
        viewModel.getCallbackRequests(page);
        viewModel.shouldUpdate();
    }

    @Override
    public void onPause() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(callRequestsReceiver);

        super.onPause();
    }

    private void setUpObserver() {

        viewModel.notifyDataChange.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

                if (aBoolean) {
                    // hide the loading ui
                    binding.loadingContainer.setVisibility(View.GONE);

                    if(viewModel.tempCallbackRequestList.size() == 0 && page == 1) {
                        binding.noCallbackRequests.setVisibility(View.VISIBLE);
                    } else {
                        refreshCallbackList(viewModel.tempCallbackRequestList);
                    }
                }
            }
        });


        viewModel.systemDataResponseFetched.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                // check if permission is granted then go ahead and insert contact into phone book
                if(!TextUtils.isEmpty(viewModel.phone)) {
                    //((BaseAppCompatActivity)getActivity()).getSharedPreference().edit().putString(Constants.DOCTOR_24X7_NUMBER, viewModel.phone).apply();
                    PermissionsHelper ph = new PermissionsHelper();
                    if(ph.hasContactsPermission(getActivity().getApplicationContext())) {
                        ContactsProvider contactsProvider = new ContactsProvider(getContext());
                        contactsProvider.writeContact(viewModel.phone);
                    }
                }

                if(viewModel.mustUpdateApp) {
                    // Block the view of the app and shouw layout which redirects the app to the doctor app in Play Store
                }
            }
        });

        /*viewModel.callbackRequests.subscribe(flowableList -> {
            adapter.submitList(flowableList);
        });*/
    }


    private void setUpData() {
        // Our handler for received Intents. This will be called whenever an Intent
        // with an action named "custom-event-name" is broadcasted.
        callRequestsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
                String action = intent.getStringExtra("action");
                viewModel.getCallbackRequests(page);
            }
        };

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.callbackRequestList.setLayoutManager(layoutManager);
        binding.callbackRequestList.addItemDecoration(new SpacesItemDecoration(8, 16, 8, 16));
        adapter = new CallbackRequestListAdapter(viewModel.callbackRequestList.get(), CallbackListFragment.this, binding.callbackRequestList);
        // adapter = new CallbackRequestPageAdapter(viewModel.callbackRequestList.get(), CallbackListFragment.this);

        binding.callbackRequestList.setAdapter(adapter);
    }

    @Override
    public void onCallbackClicked(int callbackId) {
        Navigator.navigateToCallbackDetails(getContext(), callbackId);
    }

    private void refreshCallbackList(List<CallbackRequest> tempCallbackRequests) {

        adapter.setLoaded();
        if (page > 1) {
            viewModel.callbackRequestList.get().remove(viewModel.callbackRequestList.get().size() - 1);
            adapter.notifyItemRemoved(viewModel.callbackRequestList.get().size());
        } else {
            viewModel.callbackRequestList.get().clear();
            adapter.notifyDataSetChanged();
        }

        if (tempCallbackRequests != null && tempCallbackRequests.size() > 0) {
            //page = page + 1;
            viewModel.callbackRequestList.get().addAll(tempCallbackRequests);

            if (viewModel.callbackRequestList.get().size() > 0) {
                // Callback requests available hide the textview
                binding.noCallbackRequests.setVisibility(View.INVISIBLE);
                binding.callbackRequestList.setVisibility(View.VISIBLE);
            } else {
                // No Callback requests. Hide the recycler view
                binding.callbackRequestList.setVisibility(View.INVISIBLE);
                binding.noCallbackRequests.setVisibility(View.VISIBLE);
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
                    adapter.notifyItemRangeInserted(totalItemsCount, totalItemsCount + tempCallbackRequests.size() - 1);
                }

                //blogsRecyclerView.setAdapter(blogsAdapter);
                if (binding.callbackRequestList != null && currentItemPosition > 0) {
                    binding.callbackRequestList.smoothScrollToPosition(currentItemPosition - 1);
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
        adapter.setOnLoadMoreListener(new CallbackRequestListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                page = page + 1;
                viewModel.callbackRequestList.get().add(null);
                adapter.notifyItemInserted(viewModel.callbackRequestList.get().size() - 1);

                // make a call with current page number
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity() != null)
                            viewModel.getCallbackRequests(page);
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
}