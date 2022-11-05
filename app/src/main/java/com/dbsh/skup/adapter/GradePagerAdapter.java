package com.dbsh.skup.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dbsh.skup.views.GradeAllFragment;
import com.dbsh.skup.views.GradeTermFragment;

public class GradePagerAdapter extends FragmentStateAdapter {

    public int mCount;
    public GradePagerAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (getRealPosition(position) == 0) {
            GradeAllFragment fg = new GradeAllFragment();
            return fg;
        } else {
            GradeTermFragment fg = new GradeTermFragment();
            return fg;
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public int getRealPosition(int position) {
        return position % mCount;
    }
}
