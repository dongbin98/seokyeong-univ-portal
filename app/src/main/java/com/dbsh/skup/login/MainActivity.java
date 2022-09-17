package com.dbsh.skup.login;

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

import androidx.appcompat.app.AppCompatActivity;

import com.dbsh.skup.HttpUrlConnector;
import com.dbsh.skup.R;
import com.dbsh.skup.home.HomeActivity;
import com.dbsh.skup.user.LectureInfo;
import com.dbsh.skup.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String loginUrl = "https://sportal.skuniv.ac.kr/sportal/auth2/login.sku";
	private static final String lectUrl = "https://sportal.skuniv.ac.kr/sportal/common/selectList.sku";
	private static final String lectTimeUrl = "https://sportal.skuniv.ac.kr/sportal/common/selectOne.sku";    // 개요
	private static final String attendanceDURL = "https://sportal.skuniv.ac.kr/sportal/common/selectList.sku";
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
						Toast.makeText(MainActivity.this, "회원 정보를 불러옵니다", Toast.LENGTH_LONG).show();
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
			// 강의정보 저장하기
			if (!((User) getApplication()).getLectureInfos().isEmpty()) {
				((User) getApplication()).clearLectureInfo();
			}
			JSONArray yearList = response.getJSONArray("YEAR_LIST");	// 입학이후 모든 해 동안
			for (int i = 0; i < yearList.length(); i++) {
				for (int j = 0; j < 4; j++) {	// 1,2,여름계절,겨울계절 학기중 수강한 과목 저장
					getLectureInfo(((User) getApplication()).getToken(),
							((User) getApplication()).getId(),
							yearList.getJSONObject(i).get("value").toString(),
							Integer.toString(j));
				}
			}
			System.out.println("강의정보 저장 성공");

			// 홈페이지로 넘어가기
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);

		} catch (JSONException exception) {
			exception.printStackTrace();
		}
	}

	// 해당 학기 강의정보 가져오기
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
					String lectureCd = jsonArray.getJSONObject(i).get("SUBJ_CD").toString();
					String lectureNumber = jsonArray.getJSONObject(i).get("CLSS_NUMB").toString();
					String lectureProfessor = jsonArray.getJSONObject(i).get("PROF_NUMB").toString();
					// 담당 교수가 없으면 패스 (졸업논문및시험으로 간주)
					if(lectureProfessor.equals("null"))
						continue;
					ArrayList<String> lectureTimeAndName = getLectureTime(token, id, year, term, lectureCd, lectureNumber, lectureProfessor);

					LectureInfo lectureInfo = new LectureInfo();
					lectureInfo.setLectureCd(lectureCd);
					lectureInfo.setLectureNumber(lectureNumber);
					lectureInfo.setLectureTime(lectureTimeAndName.get(0));
					lectureInfo.setLectureName(lectureTimeAndName.get(1));
					lectureInfo.setProfessor(lectureProfessor);
					lectureInfo.setYear(year);
					lectureInfo.setTerm(term);

					System.out.println(lectureCd);

					((User) getApplication()).addLectureInfo(lectureInfo);
				}
			}
			else if(response.get("RTN_STATUS").toString().equals("0")) {
				System.out.println("해당학기 수업정보 없음");
			}
		} catch (JSONException exception) {
			exception.printStackTrace();
		}
	}

	// 해당 학기 수강 교과목 수업시간, 교과목명 가져오기
	public ArrayList<String> getLectureTime(String token, String id, String year, String term, String cd, String cn, String pi) {
		ArrayList<String> lecture = new ArrayList<>();

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
				lecture.add(MAP.get("SUBJ_TIME").toString());
				lecture.add(MAP.get("SUBJ_NM").toString());
			}
		} catch (JSONException exception) {
			exception.printStackTrace();
		}
		return lecture;
	}
}