package com.dbsh.skup.tmpstructure.views;

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
import com.dbsh.skup.common.SpinnerAdapter;
import com.dbsh.skup.databinding.AttendanceTmpFormBinding;
import com.dbsh.skup.tmpstructure.adapter.AttendanceAdapter;
import com.dbsh.skup.tmpstructure.data.UserData;
import com.dbsh.skup.tmpstructure.model.ResponseAttendanceList;
import com.dbsh.skup.tmpstructure.model.ResponseYearList;
import com.dbsh.skup.tmpstructure.viewmodels.AttendanceViewModel;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {

	private AttendanceTmpFormBinding binding;
	private AttendanceViewModel viewModel;

	private static final String attendanceDetailUrl = "https://sportal.skuniv.ac.kr/sportal/common/selectList.sku";

	String token;
	String id;
	String year;
	String term;

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
		binding = DataBindingUtil.setContentView(this, R.layout.attendance_tmp_form);
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
				String title = data.get(position).text;
				int percent = data.get(position).percent;
				String cd = data.get(position).text2;
				String numb = data.get(position).text4;
				Double time = 0.0, count = 0.0;

//				for(LectureInfo lectureInfo : user.getLectureInfos()) {
//					if(lectureInfo.getLectureCd().equals(cd)) {
//						time = Double.parseDouble(lectureInfo.getLectureTime());
//						count += 1.0;
//					}
//				}
//				time /= count;

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
			String value = Integer.toString(i) + "학기";

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

		// 출결조회 버튼
		attendanceBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				data.clear();
				attendanceBtn.setClickable(false);
				viewModel.getAttendance(token, id, year, term);
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

		attendanceBtn.setClickable(false);
		data.clear();
		viewModel.getAttendance(token, id, year, term);

		viewModel.attendanceLiveData.observe(binding.getLifecycleOwner(), new Observer<ResponseAttendanceList>() {
			@Override
			public void onChanged(ResponseAttendanceList responseAttendanceList) {
				data.add(new AttendanceAdapter.AttendanceItem(
						responseAttendanceList.getSubjNm(),
						responseAttendanceList.getSubjCd(),
						Integer.toString(responseAttendanceList.getAttendanceRate()),
						responseAttendanceList.getClssNumb(),
						responseAttendanceList.getAttendanceRate()
				));
				System.out.println(responseAttendanceList.getSubjNm());
				System.out.println(responseAttendanceList.getAttendanceRate());
				adapter.notifyDataSetChanged();
				attendanceBtn.setClickable(true);
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
