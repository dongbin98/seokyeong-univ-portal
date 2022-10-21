package com.dbsh.skup.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.dbsh.skup.R;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.LecturePlanDetailSummaryFormBinding;
import com.dbsh.skup.model.ResponseLecturePlanBookList;
import com.dbsh.skup.model.ResponseLecturePlanSummaryMap;
import com.dbsh.skup.viewmodels.LecturePlanDetailSummaryViewModel;

import java.util.ArrayList;

public class LecturePlanDetailSummaryFragment extends Fragment implements OnBackPressedListener {
	private LecturePlanDetailSummaryFormBinding binding;
	private LecturePlanDetailSummaryViewModel viewModel;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;

	private Bundle bundle;
	private String subjectCd, classNumber, lectureYear, lectureTerm, professorId, lectureName;
	String token, id;

	UserData userData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.inflate(inflater, R.layout.lecture_plan_detail_summary_form, container, false);
		viewModel = new LecturePlanDetailSummaryViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();    // 바인딩 강제 즉시실행

		homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());
		bundle = new Bundle();

		userData = ((UserData) getActivity().getApplication());

		token = userData.getToken();
		id = userData.getId();

		if (getArguments() != null) {
			subjectCd = getArguments().getString("SUBJ_CD");
			classNumber = getArguments().getString("CLSS_NUMB");
			lectureYear = getArguments().getString("LECT_YEAR");
			lectureTerm = getArguments().getString("LECT_TERM");
			professorId = getArguments().getString("STAF_NO");
			lectureName = getArguments().getString("LECT_NAME");
			bundle.putAll(getArguments());
		}

		viewModel.getLecturePlanSummary(token, id, subjectCd, classNumber, lectureYear, lectureTerm, professorId);
		viewModel.getLecturePlanBook(token, id, subjectCd, classNumber, lectureYear, lectureTerm, professorId);

		viewModel.responseLecturePlanSummaryMapLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseLecturePlanSummaryMap>() {
			@Override
			public void onChanged(ResponseLecturePlanSummaryMap responseLecturePlanSummaryMap) {
				// 강의계획서 개요 처리
			}
		});

		viewModel.responseLecturePlanBookListLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ResponseLecturePlanBookList>>() {
			@Override
			public void onChanged(ArrayList<ResponseLecturePlanBookList> responseLecturePlanBookLists) {
				for(ResponseLecturePlanBookList responseLecturePlanBookList : responseLecturePlanBookLists) {
					// 강의 참고 교재 처리
				}
			}
		});

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
