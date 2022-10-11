package com.dbsh.skup.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HomeNoticeCardAdapter extends FragmentStateAdapter {
    public ArrayList<Fragment> fragments = new ArrayList<>();

    public HomeNoticeCardAdapter(FragmentActivity fa, ArrayList<Fragment> list) {
            super(fa);
            this.fragments = list;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
            return fragments.get(position);
    }

    @Override
    public int getItemCount() {
            return fragments.size();
    }
}
