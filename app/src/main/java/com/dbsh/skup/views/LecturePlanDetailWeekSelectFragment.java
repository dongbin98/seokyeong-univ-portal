package com.dbsh.skup.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.LecturePlanDetailWeekSelectFormBinding;
import com.dbsh.skup.viewmodels.LecturePlanDetailWeekSelectViewModel;

public class LecturePlanDetailWeekSelectFragment extends Fragment implements OnBackPressedListener {
	private LecturePlanDetailWeekSelectFormBinding binding;
	private LecturePlanDetailWeekSelectViewModel viewModel;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;

	private String number, value, plan, type, report;

	@SuppressLint("SetTextI18n")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.inflate(inflater, R.layout.lecture_plan_detail_week_select_form, container, false);
		viewModel = new LecturePlanDetailWeekSelectViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();    // 바인딩 강제 즉시실행

		homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());

		if (getArguments() != null) {
			number = getArguments().getString("number");
			value = getArguments().getString("value");
			plan = getArguments().getString("plan");
			type = getArguments().getString("type");
			report = getArguments().getString("report");

			binding.lectureplanDetailWeekSelectNumber.setText(number);
			binding.lectureplanDetailWeekSelectValue.setText(value + "\n" + plan);
			binding.lectureplanDetailWeekSelectType.setText(type);
			binding.lectureplanDetailWeekSelectReport.setText(report);
		}

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
