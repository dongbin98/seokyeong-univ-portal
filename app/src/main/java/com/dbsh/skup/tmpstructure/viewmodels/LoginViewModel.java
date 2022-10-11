package com.dbsh.skup.tmpstructure.viewmodels;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.R;
import com.dbsh.skup.tmpstructure.api.RetrofitAPI;
import com.dbsh.skup.tmpstructure.api.RetrofitClient;
import com.dbsh.skup.tmpstructure.model.RequestUserData;
import com.dbsh.skup.tmpstructure.model.ResponseLogin;
import com.dbsh.skup.tmpstructure.model.ResponseUserInfo;

import java.io.IOException;

public class LoginViewModel extends ViewModel {

	public RetrofitAPI retrofitAPI;

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
		RetrofitClient retrofitClient = RetrofitClient.getInstance();
		if(retrofitClient != null) {
			retrofitAPI = RetrofitClient.getRetrofitAPI();
		}
	}

	public ResponseLogin getUserData(String loginId, String loginPassword) throws IOException {
		ResponseLogin login = null;
		RetrofitClient retrofitClient = RetrofitClient.getInstance();
		if(retrofitClient != null) {
			retrofitAPI = RetrofitClient.getRetrofitAPI();
			login = retrofitAPI.getUserData(new RequestUserData(loginId, loginPassword, "password", "sku")).execute().body();
			/* 비동기식 처리 */
//			retrofitAPI.getUserData(new RequestUserData(loginId, loginPassword, "password", "sku")).enqueue(new Callback<ResponseLogin>() {
//				@Override
//				public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
//
//					assert response.body() != null;
//					login = response.body();
//
//					if(login.getRtnStatus().equals("S")) {
//						ResponseUserInfo userInfo = login.getUserInfo();
//						List<ResponseYearList> yearList = login.getYearList();
//						System.out.println(login);
//						System.out.println(userInfo);
//						for (ResponseYearList year : yearList)
//							System.out.println(year);
//						success[0] = true;
//					}
//				}
//
//				@Override
//				public void onFailure(Call<ResponseLogin> call, Throwable t) {
//					// 서버통신 안될 때
//					success[0] = false;
//				}
//			});
		}
		return login;
	}
}
