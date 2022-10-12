package com.dbsh.skup.tmpstructure.viewmodels;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.R;
import com.dbsh.skup.tmpstructure.Service.PortalService;
import com.dbsh.skup.tmpstructure.api.PortalApi;
import com.dbsh.skup.tmpstructure.model.RequestUserData;
import com.dbsh.skup.tmpstructure.model.ResponseLogin;
import com.dbsh.skup.tmpstructure.model.ResponseUserInfo;

import java.io.IOException;

public class LoginViewModel extends ViewModel {

	public PortalApi portalApi;

	// 로그인 데이터
	public LiveData<String> loginId = new MutableLiveData<>();
	public LiveData<String> loginPassword = new MutableLiveData<>();

	private MutableLiveData<ResponseUserInfo> userMutableLiveData;

	public LiveData<String> getLoginId() {
		return loginId;
	}

	public LiveData<String> getLoginPassword() {
		return loginPassword;
	}

	public MutableLiveData<ResponseUserInfo> getUser() {
		if (userMutableLiveData == null) {
			userMutableLiveData = new MutableLiveData<>();
		}
		return userMutableLiveData;
	}

	public EditText.OnFocusChangeListener loginFocusListener() {
		return (v, hasFocus) -> {
			if (hasFocus)
				v.setBackgroundResource(R.drawable.edittext_white_focused_background);
			else
				v.setBackgroundResource(R.drawable.edittext_white_background);
		};
	}

	public void getLectureData(String token, String id, String year, String term) {
		System.out.printf("%s년 %s학기 조회", year, term);
		PortalService retrofitClient = PortalService.getInstance();
		if(retrofitClient != null) {
			portalApi = PortalService.getPortalApi();
		}
	}

	public ResponseLogin getUserData(String loginId, String loginPassword) throws IOException {
		ResponseLogin login = null;
		PortalService retrofitClient = PortalService.getInstance();
		if(retrofitClient != null) {
			portalApi = PortalService.getPortalApi();
			login = portalApi.getUserData(new RequestUserData(loginId, loginPassword, "password", "sku")).execute().body();
		}
		return login;
	}
}
