package com.dbsh.skup.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HomeTopCardAdapter extends FragmentStateAdapter {
    private int mCount;
    public HomeTopCardAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(getRealPosition(position) == 0) {
            HomeCenterInformationFragment fg = new HomeCenterInformationFragment();
            return fg;
        }
        else if(getRealPosition(position) == 1) {
            HomeCenterTimetableFragment fg = new HomeCenterTimetableFragment();
            return fg;
        }
        else {
            HomeCenterBustimeFragment fg = new HomeCenterBustimeFragment();
            return fg;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public int getRealPosition(int position) {return position % mCount;}
}