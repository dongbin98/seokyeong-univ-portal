package com.dbsh.skup.Service;

import com.dbsh.skup.api.LoginApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginService {
	private LoginApi loginApi;
	private Retrofit retrofit;

	private final static String BASE_URL = "https://sportal.skuniv.ac.kr/sportal/";

	public LoginService() {
		retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		loginApi = retrofit.create(LoginApi.class);
	}

	public LoginApi getLoginApi() { return loginApi; }
}
