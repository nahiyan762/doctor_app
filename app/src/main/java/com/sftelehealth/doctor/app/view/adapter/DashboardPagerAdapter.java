package com.sftelehealth.doctor.app.view.adapter;

import android.content.Context;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.view.fragment.CallbackListFragment;
import com.sftelehealth.doctor.app.view.fragment.CompletedConsultListFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by Rahul on 19/12/17.
 */

public class DashboardPagerAdapter extends FragmentStatePagerAdapter {

    int[] titles = {
            R.string.completed_consults,
            R.string.callback_requests
    };

    Context context;

    public DashboardPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment f = null;

        switch (position) {
            case 0:
                f = CompletedConsultListFragment.getInstance();
                break;
            case 1:
                f = CallbackListFragment.getInstance();
        }
        return f;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(titles[position]);
    }
}
