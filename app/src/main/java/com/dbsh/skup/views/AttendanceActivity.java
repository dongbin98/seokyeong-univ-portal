package com.dbsh.skup.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.AttendanceAdapter;
import com.dbsh.skup.adapter.SpinnerAdapter;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.AttendanceFormBinding;
import com.dbsh.skup.model.ResponseAttendanceList;
import com.dbsh.skup.model.ResponseYearList;
import com.dbsh.skup.viewmodels.AttendanceViewModel;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {

	private AttendanceFormBinding binding;
	private AttendanceViewModel viewModel;

	String token;
	String id;
	String year;
	String term;
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.setContentView(this, R.layout.attendance_form);
		binding.setLifecycleOwner(this);
		viewModel = new AttendanceViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();	// 바인딩 강제 즉시실행

		userData = ((UserData) getApplication());

		data = new ArrayList<>();

		Intent intent = getIntent();
		token = userData.getToken();
		id = userData.getId();
		year = userData.getSchYear();
		term = userData.getSchTerm();

		Toolbar mToolbar = binding.attendanceToolbar;
		setSupportActionBar(mToolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("");

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

				Intent detailIntent = new Intent(AttendanceActivity.this, AttendanceDetailActivity.class);
				detailIntent.putExtra("TITLE", title);
				detailIntent.putExtra("PERCENT", percent);
				detailIntent.putExtra("CD", cd);
				detailIntent.putExtra("NUMB", numb);
				detailIntent.putExtra("TIME", time);
				detailIntent.putExtra("YEAR", year);
				detailIntent.putExtra("TERM", term);
				startActivity(detailIntent);
			}
		});

		attendanceList.setLayoutManager(new LinearLayoutManager(this));
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

		SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, spinnerItem);
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

		SpinnerAdapter spinnerAdapter2 = new SpinnerAdapter(this, spinnerItem2);
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
				data.clear();
				nowCount = 0;
				totalCount = 0;
				viewModel.getAttendance(token, id, year, term);
			}
		});

		adapter.setAdapterClickable(false);
		attendanceBtn.setClickable(false);
		data.clear();
		nowCount = 0;
		totalCount = 0;
		viewModel.getAttendance(token, id, year, term);

		viewModel.totalSizeLiveData.observe(binding.getLifecycleOwner(), new Observer<Integer>() {
			@Override
			public void onChanged(Integer integer) {
				totalCount = integer;
			}
		});

		viewModel.attendanceLiveData.observe(binding.getLifecycleOwner(), new Observer<ResponseAttendanceList>() {
			@SuppressLint("NotifyDataSetChanged")
			@Override
			public void onChanged(ResponseAttendanceList responseAttendanceList) {
				if (responseAttendanceList != null) {
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
				adapter.notifyDataSetChanged();
				if(totalCount == nowCount) {
					attendanceBtn.setClickable(true);
					adapter.setAdapterClickable(true);
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
				finish();
				return true;
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
