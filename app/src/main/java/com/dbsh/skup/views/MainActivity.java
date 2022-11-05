package com.dbsh.skup.views;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.dbsh.skup.R;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.LoginFormBinding;
import com.dbsh.skup.model.ResponseLogin;
import com.dbsh.skup.viewmodels.LoginViewModel;

public class MainActivity extends AppCompatActivity {
	private long time = 0;
	private LoginFormBinding binding;
	private LoginViewModel viewModel;

	public static UserData userData = new UserData();
	public TranslateAnimation anim;
	String userId, userPw;
	Boolean checked;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.setContentView(this, R.layout.login_form);
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
				binding.loginBtn.setClickable(false);
				userId = binding.loginID.getText().toString();
				userPw = binding.loginPW.getText().toString();
				viewModel.getUserData(userId, userPw);
			}
		});

		viewModel.loginData.observe(this, new Observer<ResponseLogin>() {
			@Override
			public void onChanged(ResponseLogin responseLogin) {
				if (responseLogin != null) {
					if (binding.loginAuto.isChecked()) {
						SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
						SharedPreferences.Editor autoLoginEdit = auto.edit();
						autoLoginEdit.putString("userId", userId);
						autoLoginEdit.putString("userPw", userPw);
						autoLoginEdit.putBoolean("checked", true);
						autoLoginEdit.apply();
					} else {
						SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
						SharedPreferences.Editor autoLoginEdit = auto.edit();
						autoLoginEdit.putBoolean("checked", false);
						autoLoginEdit.apply();
					}
					/* 유저데이터 저장 */
					((UserData) getApplication()).setId(responseLogin.getUserInfo().getId());
					((UserData) getApplication()).setKorName(responseLogin.getUserInfo().getKorName());
					((UserData) getApplication()).setPhoneNumber(responseLogin.getUserInfo().getPhoneMobile());
					((UserData) getApplication()).setMajor(responseLogin.getUserInfo().getColNm(), responseLogin.getUserInfo().getTeamNm());
					((UserData) getApplication()).setEmailAddress(responseLogin.getUserInfo().getEmail());
					((UserData) getApplication()).setWebmailAddress(responseLogin.getUserInfo().getWebmailId());
					((UserData) getApplication()).setTutorName(responseLogin.getUserInfo().getTutorName());
					((UserData) getApplication()).setSchInfo(responseLogin.getUserInfo().getSchYear(), responseLogin.getUserInfo().getSchTerm(), responseLogin.getUserInfo().getSchyr(), responseLogin.getUserInfo().getSchRegStatNm());
					((UserData) getApplication()).setToken(responseLogin.getAccessToken());
					((UserData) getApplication()).setYearList(responseLogin.getYearList());

					binding.loginBtn.setClickable(true);
					Intent intent = new Intent(MainActivity.this, HomeActivity.class);
					startActivity(intent);
				}
			}
		});

		viewModel.loginState.observe(binding.getLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				switch (s) {
					case "success":
						Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
						break;
					case "fail":
						Toast.makeText(getApplicationContext(), "학번 및 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
						binding.loginID.setBackgroundResource(R.drawable.edittext_white_error_background);
						binding.loginPW.setBackgroundResource(R.drawable.edittext_white_error_background);
						binding.loginID.startAnimation(shakeError());
						binding.loginPW.startAnimation(shakeError());
						binding.loginBtn.setClickable(true);
						break;
					case "network":
						Toast.makeText(getApplicationContext(), "네트워크에 연결되어 있지 않습니다", Toast.LENGTH_SHORT).show();
						binding.loginBtn.setClickable(true);
						break;
				}
			}
		});
	}

	public TranslateAnimation shakeError() {
		anim = new TranslateAnimation(0, 10, 0, 0);
		anim.setDuration(500);
		anim.setInterpolator(new CycleInterpolator(7));
		return anim;
	}

	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
		userId = auto.getString("userId", null);
		userPw = auto.getString("userPw", null);
		checked = auto.getBoolean("checked", false);

		if(checked) {
			binding.loginAuto.setChecked(true);
			binding.loginID.setText(userId);
			binding.loginPW.setText(userPw);
		} else {
			binding.loginAuto.setChecked(false);
			binding.loginID.setText("");
			binding.loginPW.setText("");
		}
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
