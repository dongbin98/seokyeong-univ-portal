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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.LecturePlanDetailWeekAdapter;
import com.dbsh.skup.adapter.LinearLayoutManagerWrapper;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.LecturePlanDetailWeekFormBinding;
import com.dbsh.skup.model.ResponseLecturePlanWeekList;
import com.dbsh.skup.viewmodels.LecturePlanDetailWeekViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class LecturePlanDetailWeekFragment extends Fragment implements OnBackPressedListener {
	private LecturePlanDetailWeekFormBinding binding;
	private LecturePlanDetailWeekViewModel viewModel;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;

	// this Fragment
	private Fragment LecturePlanWeekFragment;

	private Bundle bundle;
	private String token, id, subjectCd, classNumber, lectureYear, lectureTerm, professorId, lectureName;
	int totalCount;
	int nowCount;

	RecyclerView lecturePlanDetailWeekRecyclerview;
	ArrayList<LecturePlanDetailWeekAdapter.LecturePlanDetailWeekItem> data;          // 원본
	LecturePlanDetailWeekAdapter adapter;

	UserData userData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.inflate(inflater, R.layout.lecture_plan_detail_week_form, container, false);
		viewModel = new LecturePlanDetailWeekViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();    // 바인딩 강제 즉시실행

		homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());
		LecturePlanWeekFragment = this;
		bundle = new Bundle();

		userData = ((UserData) getActivity().getApplication());

		token = userData.getToken();
		id = userData.getId();

		data = new ArrayList<>();

		if (getArguments() != null) {
			subjectCd = getArguments().getString("SUBJ_CD");
			classNumber = getArguments().getString("CLSS_NUMB");
			lectureYear = getArguments().getString("LECT_YEAR");
			lectureTerm = getArguments().getString("LECT_TERM");
			professorId = getArguments().getString("STAF_NO");
			lectureName = getArguments().getString("LECT_NAME");
		}

		lecturePlanDetailWeekRecyclerview = binding.lectureplanDetailWeekRecyclerview;
		adapter = new LecturePlanDetailWeekAdapter(data);
		LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false);
		lecturePlanDetailWeekRecyclerview.setLayoutManager(linearLayoutManagerWrapper);
		lecturePlanDetailWeekRecyclerview.setAdapter(adapter);

		adapter.setAdapterClickable(false);
		adapter.dataClear();
		data.clear();
		adapter.notifyDataSetChanged();
		nowCount = 0;
		totalCount = 0;
		viewModel.getLecturePlanWeek(token, id, subjectCd, classNumber, lectureYear, lectureTerm, professorId);

		viewModel.responseLecturePlanWeekListLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ResponseLecturePlanWeekList>>() {
			@Override
			public void onChanged(ArrayList<ResponseLecturePlanWeekList> responseLecturePlanWeekLists) {
				if(responseLecturePlanWeekLists != null) {
					for (ResponseLecturePlanWeekList responseLecturePlanWeekList : responseLecturePlanWeekLists) {
						if (totalCount-1 <= nowCount) {
							data.add(new LecturePlanDetailWeekAdapter.LecturePlanDetailWeekItem(
									responseLecturePlanWeekList.getWeekNm(),
									responseLecturePlanWeekList.getWeekTitl01(),
									responseLecturePlanWeekList.getLectPlan01(),
									responseLecturePlanWeekList.getLectMthd01(),
									responseLecturePlanWeekList.getRepotEtc01(),
									true
							));
						} else {
							data.add(new LecturePlanDetailWeekAdapter.LecturePlanDetailWeekItem(
									responseLecturePlanWeekList.getWeekNm(),
									responseLecturePlanWeekList.getWeekTitl01(),
									responseLecturePlanWeekList.getLectPlan01(),
									responseLecturePlanWeekList.getLectMthd01(),
									responseLecturePlanWeekList.getRepotEtc01(),
									false
							));
						}
						nowCount++;
						data.sort(new Comparator<LecturePlanDetailWeekAdapter.LecturePlanDetailWeekItem>() {
							@Override
							public int compare(LecturePlanDetailWeekAdapter.LecturePlanDetailWeekItem data, LecturePlanDetailWeekAdapter.LecturePlanDetailWeekItem t1) {
								int result = 1;
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

								if(Integer.parseInt(data.number) < Integer.parseInt(t1.number))
									result = -1;
								return result;
							}
						});
						adapter.notifyItemInserted(data.size());
						if (totalCount == nowCount) {
							System.out.println("All List Done!");
							adapter.setAdapterClickable(true);
						}
					}
				}
			}
		});

		viewModel.totalSizeLiveData.observe(getViewLifecycleOwner(), new Observer<Integer>() {
			@Override
			public void onChanged(Integer integer) {
				totalCount = integer;
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
