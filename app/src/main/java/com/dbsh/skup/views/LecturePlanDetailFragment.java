package com.dbsh.skup.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.LecturePlanPagerAdapter;
import com.dbsh.skup.databinding.LecturePlanDetailFormBinding;
import com.dbsh.skup.viewmodels.LecturePlanDetailViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LecturePlanDetailFragment extends Fragment implements OnBackPressedListener {
	private LecturePlanDetailFormBinding binding;
	private LecturePlanDetailViewModel viewModel;

	private TabLayout tabs;
	private ViewPager2 mPager;
	private FragmentStateAdapter pagerAdapter;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;

	private int numPage = 2;
	private Bundle bundle;
	private String subjectCd, classNumber, lectureYear, lectureTerm, professorId, lectureName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.inflate(inflater, R.layout.lecture_plan_detail_form, container, false);
		viewModel = new LecturePlanDetailViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();    // 바인딩 강제 즉시실행

		homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());
		bundle = new Bundle();

		if(getArguments() != null) {
			subjectCd = getArguments().getString("SUBJ_CD");
			classNumber = getArguments().getString("CLSS_NUMB");
			lectureYear = getArguments().getString("LECT_YEAR");
			lectureTerm = getArguments().getString("LECT_TERM");
			professorId = getArguments().getString("STAF_NO");
			lectureName = getArguments().getString("LECT_NAME");
			bundle.putAll(getArguments());
		}

		Toolbar mToolbar = binding.lectureplanDetailToolbar;

		((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
		((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		((HomeActivity) getActivity()).getSupportActionBar().setTitle("");
		binding.lectureplanDetailToolbarName.setText(lectureName);
		tabs = binding.lectureplanDetailTab;
		String[] titles = new String[]{"개요", "주차별 계획"};

		mPager = binding.lectureplanDetailViewpager;
		pagerAdapter = new LecturePlanPagerAdapter(getActivity(), numPage, bundle);
		mPager.setAdapter(pagerAdapter);
		mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
		mPager.setCurrentItem(0);
		mPager.setOffscreenPageLimit(2);

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
			}
		});

		new TabLayoutMediator(tabs, mPager, new TabLayoutMediator.TabConfigurationStrategy() {
			@Override
			public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
				tab.setText(titles[position]);
			}
		}).attach();

		return binding.getRoot();
	}

	@Override
	public void onBackPressed() {
		homeLeftContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
		homeLeftContainer.getChildFragmentManager().popBackStackImmediate();
		homeLeftContainer.popFragment();
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		((HomeActivity) context).setOnBackPressedListner(this);
	}
}