package com.dbsh.skup.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.dbsh.skup.R;
import com.dbsh.skup.user.User;

import me.relex.circleindicator.CircleIndicator3;

public class HomeCenterFragment extends Fragment {
    private static final String timetableURL = "https://sportal.skuniv.ac.kr/sportal/common/selectList.sku";

	com.dbsh.skup.user.User user;

    private ViewPager2 mPager, mPager2, mPager3;
    private CircleIndicator3 mIndicator;
    private FragmentStateAdapter pagerAdapter, pagerAdapter2, pagerAdapter3;
    private int num_page = 3;
    private int num_page2 = 3;
    private int num_page3 = 3;

    ImageButton main_home_quick_btn1, main_home_quick_btn2, main_home_quick_btn3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_center_form, container, false);
	    user = ((User) getActivity().getApplication());
        main_home_quick_btn1 = (ImageButton) rootView.findViewById(R.id.main_home_quick_btn1);
        main_home_quick_btn2 = (ImageButton) rootView.findViewById(R.id.main_home_quick_btn2);
        main_home_quick_btn3 = (ImageButton) rootView.findViewById(R.id.main_home_quick_btn3);
        // 출결
        main_home_quick_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), AttendanceTmpActivity.class);
//                startActivity(intent);
            }
        });
        // 학사일정
        main_home_quick_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        // QR
        main_home_quick_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), QRActivity.class);
//                startActivity(intent);
            }
        });


        mPager = rootView.findViewById(R.id.viewpager);
        pagerAdapter = new MainCardAdapter(getActivity(), num_page);
        mPager.setAdapter(pagerAdapter);
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(3);

        mIndicator = rootView.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page,0);

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position%num_page);
            }
        });

        mPager2 = rootView.findViewById(R.id.viewpager2);
        pagerAdapter2 = new MainCardAdapter2(getActivity(), num_page2);
        mPager2.setAdapter(pagerAdapter2);
        mPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager2.setCurrentItem(0);
        mPager2.setOffscreenPageLimit(3);

        mPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager2.setCurrentItem(position);
                }
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        mPager3 = rootView.findViewById(R.id.viewpager3);
        pagerAdapter3 = new MainCardAdapter3(getActivity(), num_page3);
        mPager3.setAdapter(pagerAdapter3);
        mPager3.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager3.setCurrentItem(0);
        mPager3.setOffscreenPageLimit(3);

        mPager3.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager3.setCurrentItem(position);
                }
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        return rootView;
    }
}