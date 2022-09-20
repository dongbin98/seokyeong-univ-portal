package com.dbsh.skup.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.HttpUrlConnector;
import com.dbsh.skup.R;
import com.dbsh.skup.adapter.SpinnerAdapter;
import com.dbsh.skup.user.LectureInfo;
import com.dbsh.skup.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {
	private static final String attendanceDetailUrl = "https://sportal.skuniv.ac.kr/sportal/common/selectList.sku";

	String token;
	String id;
	String year;
	String term;

	List<AttendanceAdapter.AttendanceItem> data;
	ArrayList<String> cdList = new ArrayList<>();   // 학수번호 따로 저장
	ArrayList<String> numbList = new ArrayList<>(); // 분반번호 따로 저장
	AttendanceAdapter adapter;
	RecyclerView attendanceList;
	Spinner attendanceSpinner, attendanceSpinner2;
	Button attendanceBtn;
	ArrayList<String> spinnerItem, spinnerItem2;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendance_form);
		user = ((User) getApplication());

		data = new ArrayList<>();

		Intent intent = getIntent();
		token = user.getToken();
		id = user.getId();
		year = user.getSchYear();
		term = user.getSchTerm();

		Toolbar mToolbar = (Toolbar) findViewById(R.id.attendance_toolbar);
		setSupportActionBar(mToolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("");

		attendanceSpinner = (Spinner) findViewById(R.id.attendance_spinner);
		attendanceSpinner2 = (Spinner) findViewById(R.id.attendance_spinner2);
		attendanceBtn = (Button) findViewById(R.id.attendance_btn);
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
				Double time = 0.0, count = 0.0;

				for(LectureInfo lectureInfo : user.getLectureInfos()) {
					if(lectureInfo.getLectureCd().equals(cd)) {
						time = Double.parseDouble(lectureInfo.getLectureTime());
						count += 1.0;
					}
				}
				time /= count;

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
		for (LectureInfo lectureInfo : user.getLectureInfos()) {
			String value = lectureInfo.getYear() + "년";

			if (!spinnerItem.contains(value)) {
				spinnerItem.add(value);
			}
		}

		spinnerItem2 = new ArrayList<>();
		for (LectureInfo lectureInfo : user.getLectureInfos()) {
			String value = lectureInfo.getTerm() + "학기";

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

		attendanceBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				attendanceBtn.setClickable(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						adapter.dataClear();
						if(getAttendance(token, id, year, term))
							attendanceBtn.setClickable(true);
						// 쓰레드 안에서 UI 변경 시 필요
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								adapter.notifyDataSetChanged();
							}
						});
					}
				}).start();
			}
		});

        new Thread(new Runnable() {
            @Override
            public void run() {
	            attendanceBtn.setClickable(false);
                adapter.dataClear();
                if(getAttendance(token, id, year, term))
	                attendanceBtn.setClickable(true);
				// 쓰레드 안에서 UI 변경 시 필요
	            runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapter.notifyDataSetChanged();
					}
				});
            }
        }).start();
	}

	// 과목 별 간략한 정보
	public boolean getAttendance(String token, String id, String year, String term) {
		cdList.clear();
		numbList.clear();
		data.clear();

		for (LectureInfo lectureInfo : user.getLectureInfos()) {
			if (lectureInfo.getYear().equals(year) && lectureInfo.getTerm().equals(term)) {
				if(!cdList.contains(lectureInfo.getLectureCd())) {  // 2일이상 수업하는 과목 중복 표시 방지
					int percent = getDetailAttendance(token, id, year, term, lectureInfo.getLectureCd(), lectureInfo.getLectureNumber());
					cdList.add(lectureInfo.getLectureCd());
					numbList.add(lectureInfo.getLectureNumber());
					data.add(new AttendanceAdapter.AttendanceItem(
							lectureInfo.getLectureName(),
							(lectureInfo.getLectureCd() + "-" + lectureInfo.getLectureNumber()),
							Integer.toString(percent),
							percent)
					);
				}
			}
		}
		return true;
	}
	// 과목별 상세 정보
	public int getDetailAttendance(String token, String id, String year, String term, String CD, String NUMB) {
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
			JSONObject response = HttpUrlConnector.getInstance().getResponseWithToken(attendanceDetailUrl, payload, token);

			if (response.get("RTN_STATUS").toString().equals("S")) {

				JSONArray jsonArray = response.getJSONArray("LIST");
				int count = Integer.parseInt(response.get("COUNT").toString());

				double all = 0.0;       // 총 수업시간
				double absn = 0.0;      // 지각 및 결석시간
				double time = 0.0;      // 하루 수업시간
				double cnt = 0.0;		// 주 수업횟수
				double total;			// 수강 수업시간

				for(LectureInfo lectureInfo : user.getLectureInfos()) {
					if(lectureInfo.getLectureCd().equals(CD)) {
						time = Double.parseDouble(lectureInfo.getLectureTime());
						cnt += 1.0;
					}
				}
				time /= cnt;
				for (int i = 0; i < count; i++) {
					System.out.println(i);
					all += time;
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
