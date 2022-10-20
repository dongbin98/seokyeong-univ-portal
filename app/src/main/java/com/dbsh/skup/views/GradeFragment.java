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
import com.dbsh.skup.adapter.GradePagerAdapter;
import com.dbsh.skup.databinding.GradeFormBinding;
import com.dbsh.skup.viewmodels.GradeViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class GradeFragment extends Fragment implements OnBackPressedListener {
	private long time = 0;
	private GradeFormBinding binding;
	private GradeViewModel viewModel;

	private TabLayout tabs;
	private ViewPager2 mPager;
	private FragmentStateAdapter pagerAdapter;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;

	private int numPage = 2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.inflate(inflater, R.layout.grade_form, container, false);
		viewModel = new GradeViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();    // 바인딩 강제 즉시실행

		homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());

		Toolbar mToolbar = binding.gradeToolbar;

		((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
		((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		((HomeActivity) getActivity()).getSupportActionBar().setTitle("");

		tabs = binding.gradeTab;
		String[] titles = new String[]{"전체", "학기별"};

		mPager = binding.gradeViewpager;
		pagerAdapter = new GradePagerAdapter(getActivity(), numPage);
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