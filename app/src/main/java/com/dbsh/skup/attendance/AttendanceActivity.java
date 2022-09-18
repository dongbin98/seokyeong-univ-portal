package com.dbsh.skup.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.HttpUrlConnector;
import com.dbsh.skup.R;
import com.dbsh.skup.user.LectureInfo;
import com.dbsh.skup.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {
    private static final String attendanceDURL = "https://sportal.skuniv.ac.kr/sportal/common/selectList.sku";

    String token;
    String id;
    String year;
    String term;

    List<AttendanceAdapter.AttendanceItem> data;
    ArrayList<String> cdList = new ArrayList<>();   // 학수번호 따로 저장
    ArrayList<String> numbList = new ArrayList<>(); // 분반번호 따로 저장
    AttendanceAdapter adapter;
    RecyclerView attendanceList;
    Spinner attendanceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_form);

        data = new ArrayList<>();

        Intent intent = getIntent();
        token = ((User) getApplication()).getToken();
        id = ((User) getApplication()).getId();
        year = ((User) getApplication()).getSchYear();
        term = ((User) getApplication()).getSchTerm();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.attendance_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        attendanceSpinner = (Spinner) findViewById(R.id.attendance_spinner);
        attendanceList = (RecyclerView) findViewById(R.id.attendance_recyclerview);
        adapter = new AttendanceAdapter(data);

        adapter.setOnItemClickListener(new AttendanceAdapter.OnItemClickListener() {
            // 아이템 클릭시 토스트메시지
            @Override
            public void onItemClick(View v, int position) {
                String title = data.get(position).text;
                int percent = data.get(position).percent;
                String cd = cdList.get(position);
                String numb = numbList.get(position);

                double time = Double.parseDouble(((User) getApplication()).getLectureInfos().get(position).getLectureTime());

                Intent detailIntent = new Intent(AttendanceActivity.this, AttendanceDetailActivity.class);
                detailIntent.putExtra("TITLE", title);
                detailIntent.putExtra("PERCENT", percent);
                detailIntent.putExtra("CD", cd);
                detailIntent.putExtra("NUMB", numb);
                detailIntent.putExtra("TIME", time);
                detailIntent.putExtra("YEAR", "2018");
                detailIntent.putExtra("TERM", "2");
                startActivity(detailIntent);
            }
        });

        ArrayList<String> spinnerItem = new ArrayList<>();
        for (LectureInfo lectureInfo : ((User) getApplication()).getLectureInfos()) {
            String value = lectureInfo.getYear() + "년 " + lectureInfo.getTerm() + "학기";

            if (!spinnerItem.contains(value)) {
                spinnerItem.add(value);
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItem);
        attendanceSpinner.setAdapter(spinnerAdapter);
        attendanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = spinnerItem.get(i).substring(0, 4);
                term = spinnerItem.get(i).substring(6, 7);
                System.out.println(year + term);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        attendanceSpinner.setEnabled(false);
                        adapter.dataClear();
                        if (getAttendance(token, id, year, term))
                            // 쓰레드 안에서 UI 변경 시 필요
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    attendanceSpinner.setEnabled(true);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                    }
                }).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                year = ((User) getApplication()).getSchYear();
                term = ((User) getApplication()).getSchTerm();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        attendanceSpinner.setEnabled(false);
                        adapter.dataClear();
                        if (getAttendance(token, id, year, term))
                            // 쓰레드 안에서 UI 변경 시 필요
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    attendanceSpinner.setEnabled(true);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                    }
                }).start();
            }
        });

        attendanceList.setLayoutManager(new LinearLayoutManager(this));
        attendanceList.setAdapter(adapter);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                adapter.dataClear();
//                if(getAttendance(token, id, "2018", "2"))
//                    // 쓰레드 안에서 UI 변경 시 필요
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapter.notifyDataSetChanged();
//                        }
//                    });
//            }
//        }).start();
    }

    // 과목 별 간략한 정보
    public boolean getAttendance(String token, String id, String year, String term) {

        int i = 0;
        for (LectureInfo lectureInfo : ((User) getApplication()).getLectureInfos()) {
            if (lectureInfo.getYear().equals(year) && lectureInfo.getTerm().equals(term)) {
                int percent = getDetailAttendance(token, id, year, term,
                        lectureInfo.getLectureCd(),
                        lectureInfo.getLectureNumber(),
                        i);
                cdList.add(lectureInfo.getLectureCd());
                numbList.add(lectureInfo.getLectureNumber());
                data.add(new AttendanceAdapter.AttendanceItem(
                        lectureInfo.getLectureName(),
                        (lectureInfo.getLectureCd() + "-" + lectureInfo.getLectureNumber()),
                        Integer.toString(percent),
                        percent)
                );
                i++;
            }
        }
        return true;
    }
    // 과목별 상세 정보
    public int getDetailAttendance(String token, String id, String year, String term, String CD, String NUMB, int number) {
        ArrayList<ArrayList<String>> subArray = new ArrayList<>();
        int percent = 0;
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

                double all = 0;
                double absn = 0;
                double total = 0;
                for (int i = 0; i < count; i++) {
                    all += Double.parseDouble(((User) getApplication()).getLectureInfos().get(number).getLectureTime());
                    absn += Double.parseDouble(jsonArray.getJSONObject(i).get("ABSN_TIME").toString());
                }
                total = all - absn;
                percent = (int) (total / all * 100);
            }
            return percent;
        } catch (JSONException | NullPointerException exception) {
            exception.printStackTrace();
            return 0;
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
