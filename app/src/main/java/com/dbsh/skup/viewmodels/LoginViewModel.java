package com.dbsh.skup.viewmodels;

import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.R;
import com.dbsh.skup.client.LoginClient;
import com.dbsh.skup.api.LoginApi;
import com.dbsh.skup.model.RequestUserData;
import com.dbsh.skup.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

	public LoginApi loginApi;
	public MutableLiveData<String> loginState = new MutableLiveData<>();
	public MutableLiveData<ResponseLogin> loginData = new MutableLiveData<>();

	public EditText.OnFocusChangeListener loginFocusListener() {
		return (v, hasFocus) -> {
			if (hasFocus)
				v.setBackgroundResource(R.drawable.edittext_white_focused_background);
			else
				v.setBackgroundResource(R.drawable.edittext_white_background);
		};
	}

	public void getUserData(String loginId, String loginPassword) {
		LoginClient loginClient = new LoginClient();
		loginApi = loginClient.getLoginApi();
		loginApi.getUserData(new RequestUserData(loginId, loginPassword, "password", "sku")).enqueue(new Callback<ResponseLogin>() {
			@Override
			public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
				if (response.isSuccessful()) {
					if (response.body().getRtnStatus().equals("S")) {
						loginData.setValue(response.body());
						loginState.setValue("success");
					} else {
						loginState.setValue("fail");
					}
				} else {
					loginState.setValue("fail");
				}
			}

			@Override
			public void onFailure(Call<ResponseLogin> call, Throwable t) {
				System.out.println(t);
				loginState.setValue("network");
			}
		});
	}
}
