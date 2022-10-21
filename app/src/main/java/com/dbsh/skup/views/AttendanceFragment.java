package com.dbsh.skup.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.AttendanceAdapter;
import com.dbsh.skup.adapter.LinearLayoutManagerWrapper;
import com.dbsh.skup.adapter.SpinnerAdapter;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.AttendanceFormBinding;
import com.dbsh.skup.model.ResponseAttendanceList;
import com.dbsh.skup.model.ResponseYearList;
import com.dbsh.skup.viewmodels.AttendanceViewModel;

import java.util.ArrayList;
import java.util.List;

public class AttendanceFragment extends Fragment implements OnBackPressedListener {

	private AttendanceFormBinding binding;
	private AttendanceViewModel viewModel;

	// this Fragment
	private Fragment AttendanceFragment;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;
	private HomeCenterContainer homeCenterContainer;

	String token;
	String id;
	String year;
	String term;
	String type;
	int totalCount;
	int nowCount;

	List<AttendanceAdapter.AttendanceItem> data;
	AttendanceAdapter adapter;
	RecyclerView attendanceList;
	Spinner attendanceSpinner, attendanceSpinner2;
	ImageButton attendanceBtn;
	ArrayList<String> spinnerItem, spinnerItem2;
	UserData userData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.inflate(inflater, R.layout.attendance_form, container, false);
		viewModel = new AttendanceViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();	// 바인딩 강제 즉시실행

		userData = ((UserData) getActivity().getApplication());
		if(getArguments() != null) {
			type = getArguments().getString("type");
		}

		AttendanceFragment = this;
		if(type.equals("left"))
			homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());
		else if(type.equals("center"))
			homeCenterContainer = ((HomeCenterContainer) this.getParentFragment());

		data = new ArrayList<>();

		token = userData.getToken();
		id = userData.getId();
		year = userData.getSchYear();
		term = userData.getSchTerm();

		Toolbar mToolbar = binding.attendanceToolbar;

		((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
		((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		((HomeActivity) getActivity()).getSupportActionBar().setTitle("");

		attendanceSpinner = binding.attendanceSpinner;
		attendanceSpinner2 = binding.attendanceSpinner2;
		attendanceBtn = binding.attendanceBtn;
		attendanceList = binding.attendanceRecyclerview;
		adapter = new AttendanceAdapter(data);

		// 출결 상세보기
		adapter.setOnItemClickListener(new AttendanceAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View v, int position) {
				String title = data.get(position).subjName;
				int percent = data.get(position).percent;
				String cd = data.get(position).subjCd;
				String numb = data.get(position).subjNumber;
				Double time = data.get(position).subjTime;

				Bundle bundle = new Bundle();
				bundle.putString("TITLE", title);
				bundle.putInt("PERCENT", percent);
				bundle.putString("CD", cd);
				bundle.putString("NUMB", numb);
				bundle.putDouble("TIME", time);
				bundle.putString("YEAR", year);
				bundle.putString("TERM", term);
				bundle.putString("type", type);
				if(type.equals("center"))
					homeCenterContainer.pushFragment(AttendanceFragment, new AttendanceDetailFragment(), bundle);
				else if(type.equals("left")) {
					System.out.println("before : AttedanceFragment 삽입");
					homeLeftContainer.pushFragment(AttendanceFragment, new AttendanceDetailFragment(), bundle);
				}
			}
		});

		LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false);
		attendanceList.setLayoutManager(linearLayoutManagerWrapper);
		attendanceList.setAdapter(adapter);

		spinnerItem = new ArrayList<>();
		for (ResponseYearList yearList : userData.getYearList()) {
			String value = yearList.getValue() + "년";

			if (!spinnerItem.contains(value)) {
				spinnerItem.add(value);
			}
		}

		spinnerItem2 = new ArrayList<>();
		for (int i = 1; i < 5; i++) {
			String value = i + "학기";

			if (!spinnerItem2.contains(value)) {
				spinnerItem2.add(value);
			}
		}

		SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext(), spinnerItem);
		attendanceSpinner.setAdapter(spinnerAdapter);
		attendanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				year = spinnerItem.get(i).substring(0, 4);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});

		SpinnerAdapter spinnerAdapter2 = new SpinnerAdapter(getContext(), spinnerItem2);
		attendanceSpinner2.setAdapter(spinnerAdapter2);
		attendanceSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				term = spinnerItem2.get(i).substring(0, 1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});

		for(int i = 0; i < spinnerItem.size(); i++) {
			if(spinnerItem.get(i).substring(0, 4).equals(year)) {
				attendanceSpinner.setSelection(i);
			}
		}

		for(int i = 0; i < spinnerItem2.size(); i++) {
			if(spinnerItem2.get(i).substring(0, 1).equals(term)) {
				attendanceSpinner2.setSelection(i);
			}
		}

		// 출결조회 버튼
		attendanceBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				adapter.setAdapterClickable(false);
				attendanceBtn.setClickable(false);
				adapter.dataClear();
				data.clear();
				adapter.notifyDataSetChanged();
				nowCount = 0;
				totalCount = 0;
				viewModel.getAttendance(token, id, year, term);
			}
		});

		adapter.setAdapterClickable(false);
		attendanceBtn.setClickable(false);
		adapter.dataClear();
		data.clear();
		adapter.notifyDataSetChanged();
		nowCount = 0;
		totalCount = 0;
		viewModel.getAttendance(token, id, year, term);

		viewModel.totalSizeLiveData.observe(getViewLifecycleOwner(), new Observer<Integer>() {
			@Override
			public void onChanged(Integer integer) {
				totalCount = integer;
			}
		});

		viewModel.attendanceLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseAttendanceList>() {
			@SuppressLint("NotifyDataSetChanged")
			@Override
			public void onChanged(ResponseAttendanceList responseAttendanceList) {
				if (responseAttendanceList != null) {
					// 어댑터에서 null 처리
					data.add(new AttendanceAdapter.AttendanceItem(
							responseAttendanceList.getSubjNm(),
							responseAttendanceList.getSubjCd(),
							Integer.toString(responseAttendanceList.getAttendanceRate()),
							responseAttendanceList.getClssNumb(),
							responseAttendanceList.getAttendanceRate(),
							responseAttendanceList.getSubjTime()
					));
					nowCount++;
				}
				adapter.notifyItemInserted(data.size());
				if(totalCount == nowCount) {
					attendanceBtn.setClickable(true);
					adapter.setAdapterClickable(true);
				}
			}
		});
		return binding.getRoot();
	}

	@Override
	public void onBackPressed() {
		if(type.equals("center")) {
			homeCenterContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
			homeCenterContainer.getChildFragmentManager().popBackStackImmediate();
			homeCenterContainer.popFragment();
		} else if(type.equals("left")){
			homeLeftContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
			homeLeftContainer.getChildFragmentManager().popBackStackImmediate();
			homeLeftContainer.popFragment();
		}
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		((HomeActivity)context).setOnBackPressedListner(this);
	}
}
