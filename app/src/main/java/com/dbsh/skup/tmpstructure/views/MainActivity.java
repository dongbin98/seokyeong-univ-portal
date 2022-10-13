package com.dbsh.skup.tmpstructure.views;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.LoginTmpFormBinding;
import com.dbsh.skup.tmpstructure.data.UserData;
import com.dbsh.skup.tmpstructure.model.ResponseLecture;
import com.dbsh.skup.tmpstructure.model.ResponseLogin;
import com.dbsh.skup.tmpstructure.viewmodels.LoginViewModel;

import java.io.IOException;

import needle.Needle;
import needle.UiRelatedTask;

public class MainActivity extends AppCompatActivity {
	private long time = 0;
	private LoginTmpFormBinding binding;
	private LoginViewModel viewModel;

	public static UserData userData = new UserData();
	String userId, userPw;
	Boolean checked;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.setContentView(this, R.layout.login_tmp_form);
		binding.setLifecycleOwner(this);
		viewModel = new LoginViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();	// 바인딩 강제 즉시실행

		/* Auto Login */
		SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
		userId = auto.getString("userId", null);
		userPw = auto.getString("userPw", null);
		checked = auto.getBoolean("checked", false);

		if(checked) {
			binding.loginAuto.setChecked(true);
			binding.loginID.setText(userId);
			binding.loginPW.setText(userPw);
		}

		/* Login Button OnClick */
		binding.loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				userId = binding.loginID.getText().toString();
				userPw = binding.loginPW.getText().toString();

				Needle.onBackgroundThread().execute(new UiRelatedTask<String>() {
					@Override
					protected String doWork() {
						ResponseLogin response;
						try {
							response = viewModel.getUserData(userId, userPw);
							if (response.getRtnStatus().equals("S")) {
								/* 로그인 성공 시 자동로그인 저장 */
								if (binding.loginAuto.isChecked()) {
									// TODO : CheckBox is checked.
									SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
									SharedPreferences.Editor autoLoginEdit = auto.edit();
									autoLoginEdit.putString("userId", userId);
									autoLoginEdit.putString("userPw", userPw);
									autoLoginEdit.putBoolean("checked", true);
									autoLoginEdit.apply();
								}
								/* 유저데이터 저장 */
								((UserData) getApplication()).setId(response.getUserInfo().getId());
								((UserData) getApplication()).setKorName(response.getUserInfo().getKorName());
								((UserData) getApplication()).setPhoneNumber(response.getUserInfo().getPhoneMobile());
								((UserData) getApplication()).setMajor(response.getUserInfo().getColNm(), response.getUserInfo().getTeamNm());
								((UserData) getApplication()).setEmailAddress(response.getUserInfo().getEmail());
								((UserData) getApplication()).setWebmailAddress(response.getUserInfo().getWebmailId());
								((UserData) getApplication()).setTutorName(response.getUserInfo().getTutorName());
								((UserData) getApplication()).setSchInfo(response.getUserInfo().getSchYear(), response.getUserInfo().getSchTerm(), response.getUserInfo().getSchyr(), response.getUserInfo().getSchRegStatNm());
								((UserData) getApplication()).setToken(response.getAccessToken());
								System.out.println(getApplication());
								/* 강의정보 저장 */
								if (!((UserData) getApplication()).getLectureDatas().isEmpty()) {
									((UserData) getApplication()).clearLectureInfo();
								}
								for (int i = 0; i < response.getYearList().size(); i++) {
									for (int j = 4; j >= 1 ; j--) {	// 1,2,여름계절(3),겨울계절(4) 학기중 수강한 과목 저장
										ResponseLecture lecture = viewModel.getLectureData(((UserData) getApplication()).getToken(), ((UserData) getApplication()).getId(), response.getYearList().get(i).getValue(), Integer.toString(j));
										if (lecture.getRtnStatus().equals("S"))
											((UserData) getApplication()).addLectureInfo(lecture);
									}
								}
								System.out.println("강의정보 저장 성공");
								return "로그인 성공";
							} else {
								return "로그인 실패";
							}
						} catch (IOException e) {
							e.printStackTrace();
							return "로그인 실패";
						}
					}

					@Override
					protected void thenDoUiRelatedWork(String toastMessage) {
						Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
						// 홈페이지로 넘어가기
						Intent intent = new Intent(com.dbsh.skup.tmpstructure.views.MainActivity.this, com.dbsh.skup.tmpstructure.views.HomeActivity.class);
						startActivity(intent);
					}
				});
			}
		});
	}

	/* Back */
	@Override
	public void onBackPressed() {
		if(System.currentTimeMillis()-time >= 2000) {
			time=System.currentTimeMillis();
			Toast.makeText(getApplicationContext(),"한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
		}
		else if(System.currentTimeMillis()-time < 2000){
			finishAffinity();
			System.runFinalization();
			System.exit(0);
		}
	}
}
