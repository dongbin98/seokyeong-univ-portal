package com.dbsh.skup.tmpstructure.viewmodels;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.dbsh.skup.tmpstructure.model.Login;
import com.dbsh.skup.tmpstructure.model.User;

public class LoginViewModel extends BaseObservable {
	private User user;
	private Login login;

	public LoginViewModel() {
		user = new User("", "", "", "", "", "", "", "", "", "", "", "", "");
		login = new Login("", "");
	}

	@Bindable
	private String toastMessage = null;

	public String getToastMessage() {
		return toastMessage;
	}

	private void setToastMessage(String toastMessage) {
		this.toastMessage = toastMessage;
		notifyPropertyChanged(BR.toastMessage);
	}

	@Bindable
	public String getId() {
		return login.getId();
	}

	@Bindable
	public String getPassword() {
		return login.getPassword();
	}

	public void setId(String id) {
		login.setId(id);
		notifyPropertyChanged(BR.id);
	}

	public void setPassword(String password) {
		login.setPassword(password);
		notifyPropertyChanged(BR.password);
	}

	public void onLoginClicked() {
		System.out.println(getId());
		if(isInputDataValid()) {
			setToastMessage("로그인 중..");
		} else {
			setToastMessage("아이디 패스워드를 입력하세요");
		}
	}

	public boolean isInputDataValid() {
		return !TextUtils.isEmpty(getId()) && !TextUtils.isEmpty(getPassword());
	}
}
