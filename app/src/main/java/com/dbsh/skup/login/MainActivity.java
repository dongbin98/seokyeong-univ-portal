package com.dbsh.skup.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.dbsh.skup.HttpUrlConnector;
import com.dbsh.skup.R;
import com.dbsh.skup.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static String loginUrl = "https://sportal.skuniv.ac.kr/sportal/auth2/login.sku";
	private static final String lectUrl = "https://sportal.skuniv.ac.kr/sportal/common/selectList.sku";
	private static final String lectTimeUrl = "https://sportal.skuniv.ac.kr/sportal/common/selectOne.sku";    // 개요
	public static User user = new User();
	public static HttpUrlConnector connector = new HttpUrlConnector();

	String userId, userPw;
	Boolean checked;

	EditText loginId;
	EditText loginPw;
	Switch loginAuto;
	Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

	    SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
	    userId = auto.getString("userId", null);
	    userPw = auto.getString("userPw", null);
	    checked = auto.getBoolean("checked", false);

	    loginId = (EditText) findViewById(R.id.loginID);
	    loginPw = (EditText) findViewById(R.id.loginPW);
	    loginAuto = (Switch) findViewById(R.id.loginAuto);

	    loginId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View view, boolean b) {
			    if(b) {
				    loginId.setBackgroundResource(R.drawable.edittext_white_focused_background);
			    }
			    else {
				    loginId.setBackgroundResource(R.drawable.edittext_white_background);
			    }
		    }
	    });
	    loginPw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View view, boolean b) {
			    if(b) {
				    loginPw.setBackgroundResource(R.drawable.edittext_white_focused_background);
			    }
			    else {
				    loginPw.setBackgroundResource(R.drawable.edittext_white_background);
			    }
		    }
	    });

	    if(checked) {
		    loginAuto.setChecked(true);
		    loginId.setText(userId);
		    loginPw.setText(userPw);
		    new Thread(new Runnable() {
			    @Override
			    public void run() {
				    try {
					    login(userId, userPw);
				    } catch (JSONException e) {
					    e.printStackTrace();
				    }
			    }
		    }).start();
	    }
	    loginBtn = (Button) findViewById(R.id.loginBtn);
	    loginBtn.setOnClickListener(new View.OnClickListener(){
		    @Override
		    public void onClick(View view) {
			    userId = loginId.getText().toString();
			    userPw = loginPw.getText().toString();
			    if(!loginAuto.isChecked()) {
				    SharedPreferences.Editor Editor = auto.edit();
				    Editor.clear();
				    Editor.commit();
			    }
			    new Thread(new Runnable() {
				    @Override
				    public void run() {
					    try {
						    login(userId, userPw);
					    } catch (JSONException e) {
						    e.printStackTrace();
					    }
				    }
			    }).start();
		    }
	    });
    }

	public void login(String userId, String userPw) throws JSONException {
		try {
			JSONObject payload = new JSONObject();
			try {
				payload.put("username", userId);
				payload.put("password", userPw);
				payload.put("grant_type", "password");
				payload.put("userType", "sku");
			} catch (JSONException excepton) {
				excepton.printStackTrace();
			}
			JSONObject response = connector.getInstance().getResponse(loginUrl, payload);
			// 로그인 성공 실패 유무 출력
			// 쓰레드 단에서는 핸들러로 처리해줘야 Toast 메시지 출력가능
			Handler handler = new Handler(Looper.getMainLooper());
			if (response.get("RTN_STATUS").toString().equals("S")) {
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
					}
				}, 0);
			} else {
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MainActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
					}
				}, 0);
				return;
			}
			// 자동 로그인
			if (loginAuto.isChecked()) {
				// TODO : CheckBox is checked.
				SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
				SharedPreferences.Editor autoLoginEdit = auto.edit();
				autoLoginEdit.putString("userId", userId);
				autoLoginEdit.putString("userPw", userPw);
				autoLoginEdit.putBoolean("checked", true);
				autoLoginEdit.commit();
			}
			System.out.println("자동로그인 관련 성공");
			// 유저정보 저장하기
			JSONObject UserInfo = response.getJSONObject("USER_INFO");
			((User) getApplication()).setId(UserInfo.get("ID").toString());
			((User) getApplication()).setKorName(UserInfo.get("KOR_NAME").toString());
			((User) getApplication()).setPhoneNumber(UserInfo.get("PHONE_MOBILE").toString());
			((User) getApplication()).setMajor(UserInfo.get("COL_NM").toString(), UserInfo.get("TEAM_NM").toString());
			((User) getApplication()).setEmailAddress(UserInfo.get("EMAIL").toString());
			((User) getApplication()).setWebmailAddress(UserInfo.get("WEBMAIL_ID").toString());
			((User) getApplication()).setTutorName(UserInfo.get("TUTOR_NAME").toString());
			((User) getApplication()).setSchInfo(UserInfo.get("SCH_YEAR").toString(),
					UserInfo.get("SCH_TERM").toString(),
					UserInfo.get("SCHYR").toString(),
					UserInfo.get("SCH_REG_STAT_NM").toString());
			((User) getApplication()).setToken(response.get("access_token").toString());
			System.out.println("유저정보 저장 성공");
			JSONArray YearList = response.getJSONArray("YEAR_LIST");
			if (!((User) getApplication()).getYearList().isEmpty()) {
				((User) getApplication()).clearYearList();
			}
			if (!((User) getApplication()).getLectureList().isEmpty()) {
				((User) getApplication()).clearLectureList();
			}
			if (!((User) getApplication()).getLectureTimeList().isEmpty()) {
				((User) getApplication()).clearLectureTimeList();
			}
			if (!((User) getApplication()).getLectureNumberList().isEmpty()) {
				((User) getApplication()).clearLectureNumberList();
			}
			if (!((User) getApplication()).getLectureProfessorList().isEmpty()) {
				((User) getApplication()).clearLectureProfessorList();
			}
			for (int i = 0; i < YearList.length(); i++) {
				((User) getApplication()).addYearList(YearList.getJSONObject(i).get("value").toString());
			}
			System.out.println("로그인함수 완료");
			getLectureInfo(((User) getApplication()).getToken(),
					((User) getApplication()).getId(),
					((User) getApplication()).getSchYear(),
					((User) getApplication()).getSchTerm());
			// 홈페이지로 넘어가기
			//Intent intent = new Intent(this, MenuActivity.class);
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);


		} catch (JSONException exception) {
			exception.printStackTrace();
		}
	}

	// 해당 학기 수강 교과목 학수번호, 분반 가져오기
	public void getLectureInfo(String token, String id, String year, String term){
		try {
			// ----------------------------
			// URL 설정 및 접속
			// ----------------------------
			JSONObject payload = new JSONObject();
			JSONObject parameter = new JSONObject();

			parameter.put("ID", id);
			parameter.put("LECT_YEAR", year);
			parameter.put("LECT_TERM", term);
			parameter.put("STNT_NUMB", id);
			try {
				payload.put("MAP_ID", "education.ual.UAL_04004_T.select");
				payload.put("SYS_ID", "AL");
				payload.put("accessToken", token);
				payload.put("parameter", parameter);
				payload.put("path", "common/selectList");
				payload.put("programID", "main");
				payload.put("userID", id);

			} catch (JSONException exception) {
				exception.printStackTrace();
			}
			JSONObject response = connector.getInstance().getResponseWithToken(lectUrl, payload, token);

			if(response.get("RTN_STATUS").toString().equals("S")) {
				JSONArray jsonArray = response.getJSONArray("LIST");
				System.out.println("강의정보 가져오기 성공");
				int count = Integer.parseInt(response.get("COUNT").toString());

				for (int i = 0; i < count; i++) {
					((User) getApplication()).addLectureList(jsonArray.getJSONObject(i).get("SUBJ_CD").toString());
					((User) getApplication()).addLectureNumberList(jsonArray.getJSONObject(i).get("CLSS_NUMB").toString());
					((User) getApplication()).addLectureProfessorList(jsonArray.getJSONObject(i).get("PROF_NUMB").toString());
					getLecttime(token, id, year, term,
							((User) getApplication()).getLectureList().get(i),
							((User) getApplication()).getLectureNumberList().get(i),
							((User) getApplication()).getLectureProfessorList().get(i)
					);
				}
			}
			else if(response.get("RTN_STATUS").toString().equals("0")) {
				System.out.println("해당학기 수업정보 없음");
			}
		} catch (JSONException exception) {
			exception.printStackTrace();
		}
	}

	// 해당 학기 수강 교과목 수업시간 가져오기
	public void getLecttime(String token, String id, String year, String term, String cd, String cn, String pi) {
		try {
			JSONObject payload = new JSONObject();
			JSONObject parameter = new JSONObject();

			parameter.put("CLSS_NUMB", cn);
			parameter.put("LECT_YEAR", year);
			parameter.put("LECT_TERM", term);
			parameter.put("SUBJ_CD", cd);
			parameter.put("STAF_NO", pi);
			try {
				payload.put("MAP_ID", "education.ucs.UCS_03100_T.SELECT_REPORT_MAIN");
				payload.put("SYS_ID", "AL");
				payload.put("accessToken", token);
				payload.put("parameter", parameter);
				payload.put("path", "common/selectOne");
				payload.put("programID", "main");
				payload.put("userID", id);

			} catch (JSONException exception) {
				exception.printStackTrace();
			}
			JSONObject response = connector.getInstance().getResponseWithToken(lectTimeUrl, payload, token);
			if(response.get("RTN_STATUS").toString().equals("S")) {
				JSONObject MAP = response.getJSONObject("MAP");
				((User) getApplication()).addLectureTimeList(MAP.get("SUBJ_TIME").toString());
			}
		} catch (JSONException exception) {
			exception.printStackTrace();
		}
	}
}