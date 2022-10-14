package com.dbsh.skup.tmpstructure.viewmodels;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.R;
import com.dbsh.skup.tmpstructure.Service.LoginService;
import com.dbsh.skup.tmpstructure.api.LoginApi;
import com.dbsh.skup.tmpstructure.model.RequestUserData;
import com.dbsh.skup.tmpstructure.model.ResponseLogin;

import java.io.IOException;

public class LoginViewModel extends ViewModel {

	public LoginApi loginApi;

	// 로그인 데이터
	public LiveData<String> loginId = new MutableLiveData<>();
	public LiveData<String> loginPassword = new MutableLiveData<>();

	public LiveData<String> getLoginId() {
		return loginId;
	}

	public LiveData<String> getLoginPassword() {
		return loginPassword;
	}

	public EditText.OnFocusChangeListener loginFocusListener() {
		return (v, hasFocus) -> {
			if (hasFocus)
				v.setBackgroundResource(R.drawable.edittext_white_focused_background);
			else
				v.setBackgroundResource(R.drawable.edittext_white_background);
		};
	}

	public ResponseLogin getUserData(String loginId, String loginPassword) throws IOException {
		ResponseLogin login = null;
		LoginService loginService = new LoginService();
		loginApi = loginService.getLoginApi();
		login = loginApi.getUserData(new RequestUserData(loginId, loginPassword, "password", "sku")).execute().body();
		return login;
	}
}
