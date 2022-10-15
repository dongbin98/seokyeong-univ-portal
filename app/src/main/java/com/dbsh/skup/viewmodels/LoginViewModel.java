package com.dbsh.skup.viewmodels;

import android.widget.EditText;

import androidx.lifecycle.ViewModel;

import com.dbsh.skup.R;
import com.dbsh.skup.Service.LoginService;
import com.dbsh.skup.api.LoginApi;
import com.dbsh.skup.model.RequestUserData;
import com.dbsh.skup.model.ResponseLogin;

import java.io.IOException;

public class LoginViewModel extends ViewModel {

	public LoginApi loginApi;

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
