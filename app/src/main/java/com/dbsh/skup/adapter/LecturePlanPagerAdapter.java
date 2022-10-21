package com.dbsh.skup.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dbsh.skup.views.LecturePlanDetailSummaryFragment;
import com.dbsh.skup.views.LecturePlanDetailWeekFragment;

public class LecturePlanPagerAdapter extends FragmentStateAdapter {

    public int mCount;
	public Bundle mBundle;
    public LecturePlanPagerAdapter(FragmentActivity fa, int count, Bundle bundle) {
        super(fa);
        mCount = count;
	    mBundle = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (getRealPosition(position) == 0) {
            LecturePlanDetailSummaryFragment fg = new LecturePlanDetailSummaryFragment();
			fg.setArguments(mBundle);
            return fg;
        } else {
            LecturePlanDetailWeekFragment fg = new LecturePlanDetailWeekFragment();
			fg.setArguments(mBundle);
            return fg;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public int getRealPosition(int position) {
        return position % mCount;
    }
}
