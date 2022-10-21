package com.dbsh.skup.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.LecturePlanDetailWeekFormBinding;
import com.dbsh.skup.viewmodels.LecturePlanDetailWeekViewModel;

public class LecturePlanDetailWeekFragment extends Fragment implements OnBackPressedListener {
	private LecturePlanDetailWeekFormBinding binding;
	private LecturePlanDetailWeekViewModel viewModel;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;

	private Bundle bundle;
	private String subjectCd, classNumber, lectureYear, lectureTerm, professorId, lectureName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.inflate(inflater, R.layout.lecture_plan_detail_week_form, container, false);
		viewModel = new LecturePlanDetailWeekViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();    // 바인딩 강제 즉시실행

		homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());
		bundle = new Bundle();

		if (getArguments() != null) {
			subjectCd = getArguments().getString("SUBJ_CD");
			classNumber = getArguments().getString("CLSS_NUMB");
			lectureYear = getArguments().getString("LECT_YEAR");
			lectureTerm = getArguments().getString("LECT_TERM");
			professorId = getArguments().getString("STAF_NO");
			lectureName = getArguments().getString("LECT_NAME");
			bundle.putAll(getArguments());
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
