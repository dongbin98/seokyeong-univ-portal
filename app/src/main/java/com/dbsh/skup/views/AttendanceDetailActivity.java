package com.dbsh.skup.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.AttendanceDetailAdapter;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.AttendanceDetailFormBinding;
import com.dbsh.skup.model.ResponseAttendanceDetailList;
import com.dbsh.skup.viewmodels.AttendanceDetailViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import me.bastanfar.semicirclearcprogressbar.SemiCircleArcProgressBar;

public class AttendanceDetailActivity extends AppCompatActivity {

    private AttendanceDetailFormBinding binding;
    private AttendanceDetailViewModel viewModel;

    SemiCircleArcProgressBar progressBar;
    TextView attendance_subj_toolbar, attendance_detail_percent;

    String token;
    String id;
    String year;
    String term;
    String cd;
    String numb;

    String title;
    int percent;
    double time;

    int totalCount, attendanceCount, lateCount, absentCount;

    List<AttendanceDetailAdapter.AttendanceDetailItem> data;
    public AttendanceDetailAdapter adapter;
    RecyclerView attendanceDetailList;

	UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* DataBinding */
        binding = DataBindingUtil.setContentView(this, R.layout.attendance_detail_form);
        binding.setLifecycleOwner(this);
        viewModel = new AttendanceDetailViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();    // 바인딩 강제 즉시실행
        userData = ((UserData) getApplication());

        data = new ArrayList<>();

        totalCount = 0;
        attendanceCount = 0;
        lateCount = 0;
        absentCount = 0;

        Intent intent = getIntent();
        cd = intent.getStringExtra("CD");
        numb = intent.getStringExtra("NUMB");
        title = intent.getStringExtra("TITLE");
        percent = intent.getIntExtra("PERCENT", 0);
        time = intent.getDoubleExtra("TIME", time);

        token = userData.getToken();
        id = userData.getId();
        year = intent.getStringExtra("YEAR");
        term = intent.getStringExtra("TERM");

        attendance_subj_toolbar = binding.attendanceSubjToolbar;
        attendance_subj_toolbar.setText(title);

        attendance_detail_percent = binding.attendanceDetailPercent;
        attendance_detail_percent.setText(percent + "%");

        progressBar = binding.attendanceHalfProgressbar;
        if (percent == 100) {
            progressBar.setProgressBarColor(getColor(R.color.mainBlue));
        } else if (percent >= 75 && percent < 100) {
            progressBar.setProgressBarColor(getColor(R.color.mainYellow));
        } else {
            progressBar.setProgressBarColor(getColor(R.color.mainRed));
        }
        progressBar.setPercent(percent);

        Toolbar mToolbar = binding.attendanceToolbar;
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        attendanceDetailList = binding.attendanceDetailRecyclerview;
        adapter = new AttendanceDetailAdapter(data);
        attendanceDetailList.setLayoutManager(new LinearLayoutManager(this));
        attendanceDetailList.setAdapter(adapter);

        viewModel.getAttendanceDetailData(token, id, year, term, cd, numb);

        viewModel.attendanceDetailLiveData.observe(binding.getLifecycleOwner(), new Observer<ResponseAttendanceDetailList>() {
            @Override
            public void onChanged(ResponseAttendanceDetailList responseAttendanceDetailList) {
                double absnTime = Double.parseDouble(responseAttendanceDetailList.getAbsnTime());
                if (absnTime == 0) {
                    attendanceCount++;
                    data.add(new AttendanceDetailAdapter.AttendanceDetailItem(
                            "출석", "출석", responseAttendanceDetailList.getCheckDateNm(), "출석했습니다."));
                } else if (absnTime < time) {
                    lateCount++;
                    data.add(new AttendanceDetailAdapter.AttendanceDetailItem(
                            "지각", "지각", responseAttendanceDetailList.getCheckDateNm(), absnTime + "시간 지각했습니다."));
                } else {
                    absentCount++;
                    data.add(new AttendanceDetailAdapter.AttendanceDetailItem(
                            "결석", "결석", responseAttendanceDetailList.getCheckDateNm(), "결석했습니다"));
                }
                binding.attendanceDetailAtteCnt.setText(Integer.toString(attendanceCount));
                binding.attendanceDetailLateCnt.setText(Integer.toString(lateCount));
                binding.attendanceDetailAbsnCnt.setText(Integer.toString(absentCount));

                data.sort(new Comparator<AttendanceDetailAdapter.AttendanceDetailItem>() {
                    @Override
                    public int compare(AttendanceDetailAdapter.AttendanceDetailItem data, AttendanceDetailAdapter.AttendanceDetailItem t1) {
                        int result = 1;
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                        Date dateDate = null;
                        Date t1Date = null;

                        try {
                            dateDate = format.parse(data.getDate());
                            t1Date = format.parse(t1.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        int compare = dateDate.compareTo(t1Date);
                        if(compare >= 0)
                            result = -1;
                        return result;
                    }
                });
                adapter.notifyDataSetChanged();
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
