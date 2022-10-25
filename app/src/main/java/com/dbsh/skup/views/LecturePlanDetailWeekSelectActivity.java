package com.dbsh.skup.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.LecturePlanDetailWeekSelectFormBinding;
import com.dbsh.skup.viewmodels.LecturePlanDetailWeekSelectViewModel;

public class LecturePlanDetailWeekSelectActivity extends AppCompatActivity {
	private LecturePlanDetailWeekSelectFormBinding binding;
	private LecturePlanDetailWeekSelectViewModel viewModel;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;

	private String number, value, plan, type, report;

	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.setContentView(this, R.layout.lecture_plan_detail_week_select_form);
		binding.setLifecycleOwner(this);
		viewModel = new LecturePlanDetailWeekSelectViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();	// 바인딩 강제 즉시실행

		Intent intent = getIntent();

		Toolbar mToolbar = binding.lectureplanToolbar;

		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("");

		binding.lectureplanToolbarTitle.setText(intent.getStringExtra("subjectName"));
		binding.lectureplanDetailWeekSelectNumber.setText(intent.getStringExtra("number"));
		binding.lectureplanDetailWeekSelectTitle.setText(intent.getStringExtra("value"));
		binding.lectureplanDetailWeekSelectValue.setText(intent.getStringExtra("plan"));
		binding.lectureplanDetailWeekSelectType.setText(intent.getStringExtra("type"));
		binding.lectureplanDetailWeekSelectReport.setText(intent.getStringExtra("report"));
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
