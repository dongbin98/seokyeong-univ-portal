package com.dbsh.skup.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.HttpUrlConnector;
import com.dbsh.skup.R;
import com.dbsh.skup.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import me.bastanfar.semicirclearcprogressbar.SemiCircleArcProgressBar;

public class AttendanceDetailActivity extends AppCompatActivity {
    private static final String attendanceDURL = "https://sportal.skuniv.ac.kr/sportal/common/selectList.sku";

    SemiCircleArcProgressBar progressBar;
    TextView attendance_subj_toolbar, attendance_detail_percent;
    TextView attendance_detail_atte_cnt;
    TextView attendance_detail_late_cnt;
    TextView attendance_detail_absn_cnt;

    String token;
    String id;
    String year;
    String term;
    String cd;
    String numb;

    String title;
    int percent;
    double time;

    int atte_cnt, late_cnt, absn_cnt;

    List<AttendanceDetailAdapter.AttendanceDetailItem> data;
    AttendanceDetailAdapter adapter;
    RecyclerView attendanceDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_detail_form);

        data = new ArrayList<>();

        atte_cnt = 0;
        late_cnt = 0;
        absn_cnt = 0;

        Intent intent = getIntent();
        cd = intent.getStringExtra("CD");
        numb = intent.getStringExtra("NUMB");
        title = intent.getStringExtra("TITLE");
        percent = intent.getIntExtra("PERCENT", 0);
        time = intent.getDoubleExtra("TIME", time);

        token = ((User) getApplication()).getToken();
        id = ((User) getApplication()).getId();
        year = intent.getStringExtra("YEAR");
        term = intent.getStringExtra("TERM");

        System.out.printf("받아온 값 : 연도 = %s, 학기 = %s, 학수번호 = %s, 분반 = %s, 학번 = %s\n", year, term, cd, numb, id);

        attendance_detail_atte_cnt = (TextView) findViewById(R.id.attendance_detail_atte_cnt);
        attendance_detail_late_cnt = (TextView) findViewById(R.id.attendance_detail_late_cnt);
        attendance_detail_absn_cnt = (TextView) findViewById(R.id.attendance_detail_absn_cnt);

        attendance_subj_toolbar = (TextView) findViewById(R.id.attendance_subj_toolbar);
        attendance_subj_toolbar.setText(title);

        attendance_detail_percent = (TextView) findViewById(R.id.attendance_detail_percent);
        attendance_detail_percent.setText(Integer.toString(percent) + "%");

        progressBar = (SemiCircleArcProgressBar) findViewById(R.id.attendance_half_progressbar);
        if(percent == 100)
            progressBar.setProgressBarColor(getColor(R.color.mainBlue));
        else if(percent >= 75 && percent < 100)
            progressBar.setProgressBarColor(getColor(R.color.mainYellow));
        else
            progressBar.setProgressBarColor(getColor(R.color.mainRed));
        progressBar.setPercent(percent);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.attendance_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        attendanceDetailList = (RecyclerView) findViewById(R.id.attendance_detail_recyclerview);
        adapter = new AttendanceDetailAdapter(data);
        attendanceDetailList.setLayoutManager(new LinearLayoutManager(this));
        attendanceDetailList.setAdapter(adapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                adapter.dataClear();
                getDetailAttendance(token, id, year, term, cd, numb);
                /* 날짜 최신순으로 정렬 */
                data.sort(new Comparator<AttendanceDetailAdapter.AttendanceDetailItem>() {
                    @Override
                    public int compare(AttendanceDetailAdapter.AttendanceDetailItem data, AttendanceDetailAdapter.AttendanceDetailItem t1) {
                        int result = 1;
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                        Date dateDate = null;
                        Date t1Date = null;

                        try {
                            dateDate = format.parse(data.getText3());
                            t1Date = format.parse(t1.getText3());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        int compare = dateDate.compareTo(t1Date);
                        if(compare >= 0)
                            result = -1;
                        return result;
                    }
                });
                    // 쓰레드 안에서 UI 변경 시 필요
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            attendance_detail_atte_cnt.setText(Integer.toString(atte_cnt));
                            attendance_detail_late_cnt.setText(Integer.toString(late_cnt));
                            attendance_detail_absn_cnt.setText(Integer.toString(absn_cnt));
                        }
                    });
            }
        }).start();
    }

    // 과목별 상세 정보
    public void getDetailAttendance(String token, String id, String year, String term, String CD, String NUMB) {
        try {
            JSONObject payload = new JSONObject();
            JSONObject parameter = new JSONObject();

            parameter.put("CLSS_NUMB", NUMB);
            parameter.put("LECT_YEAR", year);
            parameter.put("LECT_TERM", term);
            parameter.put("STNT_NUMB", id);
            parameter.put("SUBJ_CD", CD);
            try {
                payload.put("MAP_ID", "education.ual.UAL_04004_T.select_attend_pop");
                payload.put("SYS_ID", "AL");
                payload.put("accessToken", token);
                payload.put("parameter", parameter);
                payload.put("path", "common/selectlist");
                payload.put("programID", "UAL_04004_T");
                payload.put("userID", id);

            } catch (JSONException exception) {
                exception.printStackTrace();
            }
            JSONObject response = HttpUrlConnector.getInstance().getResponseWithToken(attendanceDURL, payload, token);

            if (response.get("RTN_STATUS").toString().equals("S")) {
                JSONArray jsonArray = response.getJSONArray("LIST");
                int count = Integer.parseInt(response.get("COUNT").toString());
                for (int i = 0; i < count; i++) {
                    double div = Double.parseDouble(jsonArray.getJSONObject(i).get("ABSN_TIME").toString());
                    if(div == 0) {
                        atte_cnt += 1;
                        data.add(new AttendanceDetailAdapter.AttendanceDetailItem(
                                "출석", "출석", jsonArray.getJSONObject(i).get("CHECK_DATE_NM").toString().replace("/", "-"), "출석했습니다."));
                    }
                    else if(div < time) {
                        late_cnt += 1;
                        data.add(new AttendanceDetailAdapter.AttendanceDetailItem(
                                "지각", "지각", jsonArray.getJSONObject(i).get("CHECK_DATE_NM").toString().replace("/", "-"), (div + "시간 지각했습니다.")));
                    }
                    else {
                        absn_cnt += 1;
                        data.add(new AttendanceDetailAdapter.AttendanceDetailItem(
                                "결석", "결석", jsonArray.getJSONObject(i).get("CHECK_DATE_NM").toString().replace("/", "-"), "결석했습니다."));
                    }
                }
            } else {
				System.out.println("결과값 없음");
            }
        } catch (JSONException | NullPointerException exception) {
            exception.printStackTrace();
        }
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
