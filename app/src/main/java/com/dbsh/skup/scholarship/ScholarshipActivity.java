package com.dbsh.skup.scholarship;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

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

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class ScholarshipActivity extends AppCompatActivity {

    private static final String scholarURL = "https://sportal.skuniv.ac.kr/sportal/common/selectList.sku";
    private static final String POST = "POST";
    HttpURLConnection connection;

    String token;
    String id;
    String year;
    String term;


    Spinner scholarshipSpinner;
    Spinner scholarshipSpinner2;
    ImageButton scholarshipBtn;
    RecyclerView scholarshipRecyclerview;

	ArrayList<String> spinnerItem, spinnerItem2;
	List<ScholarshipAdapter.ScholarshipItem> data;
	ScholarshipAdapter adapter;

	User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scholarship_form);
	    user = ((User) getApplication());

        Intent intent = getIntent();

        token = user.getToken();
        id = user.getId();
        year = user.getSchYear();
        term = user.getSchTerm();

	    data = new ArrayList<>();

	    scholarshipSpinner = (Spinner) findViewById(R.id.scholarship_spinner);
	    scholarshipSpinner2 = (Spinner) findViewById(R.id.scholarship_spinner2);
	    scholarshipBtn = (ImageButton) findViewById(R.id.scholarship_btn);
	    scholarshipRecyclerview = (RecyclerView) findViewById(R.id.scholarship_recyclerview);

	    Toolbar mToolbar = (Toolbar) findViewById(R.id.scholarship_toolbar);
	    setSupportActionBar(mToolbar);

	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    getSupportActionBar().setTitle("");


	    scholarshipRecyclerview = (RecyclerView) findViewById(R.id.scholarship_recyclerview);
	    adapter = new ScholarshipAdapter(data);
	    scholarshipRecyclerview.setLayoutManager(new LinearLayoutManager(this));
	    scholarshipRecyclerview.setAdapter(adapter);

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
	    scholarshipSpinner.setAdapter(spinnerAdapter);
	    scholarshipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
			    year = spinnerItem.get(i).substring(0, 4);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> adapterView) {
		    }
	    });

	    SpinnerAdapter spinnerAdapter2 = new SpinnerAdapter(this, spinnerItem2);
	    scholarshipSpinner2.setAdapter(spinnerAdapter2);
	    scholarshipSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
			    term = spinnerItem2.get(i).substring(0, 1);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> adapterView) {
		    }
	    });

        scholarshipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        scholarshipBtn.setClickable(false);
                        if(getScholar(token, id, year, term))
	                        scholarshipBtn.setClickable(true);
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
	            scholarshipBtn.setClickable(false);
                if(getScholar(token, id, year, term))
	                scholarshipBtn.setClickable(true);
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

    public boolean getScholar(String token, String id, String year, String term) {
        try {
            JSONObject payload = new JSONObject();
            JSONObject parameter = new JSONObject();

            parameter.put("ID", id);
            parameter.put("STU_NO", id);
            parameter.put("SCH_YEAR", year);
            parameter.put("SCH_TERM", term);

            try {
                payload.put("MAP_ID", "education.uss.USS_01011_T.select_janghak");
                payload.put("SYS_ID", "AL");
                payload.put("accessToken", token);
                payload.put("parameter", parameter);
                payload.put("path", "common/selectlist");
                payload.put("programID", "USS_02013_V");
                payload.put("userID", id);

            } catch (JSONException exception) {
                exception.printStackTrace();
            }
            JSONObject response = HttpUrlConnector.getInstance().getResponseWithToken(scholarURL, payload, token);

            if(response.get("RTN_STATUS").toString().equals("S")) {
                JSONArray jsonArray = response.getJSONArray("LIST");

                int count = Integer.parseInt(response.get("COUNT").toString());

                for (int i = 0; i < count; i++) {
					data.add(new ScholarshipAdapter.ScholarshipItem(
							jsonArray.getJSONObject(i).get("SCLS_NM").toString(),
							jsonArray.getJSONObject(i).get("SCLS_NM").toString(),
							jsonArray.getJSONObject(i).get("REMK_TEXT").toString(),
							jsonArray.getJSONObject(i).get("SCLS_AMT").toString().replace(" ", "")));
                }
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return true;
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
