package com.dbsh.skup.tmpstructure.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
	private static RetrofitClient instance = null;
	private static RetrofitAPI retrofitAPI;

	private final static String BASE_URL = "https://sportal.skuniv.ac.kr/sportal/";

	private RetrofitClient() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		retrofitAPI = retrofit.create(RetrofitAPI.class);
	}

	public static RetrofitClient getInstance() {
		if(instance == null) {
			instance = new RetrofitClient();
		}
		return instance;
	}

	public static RetrofitAPI getRetrofitAPI() {
		return retrofitAPI;
	}
}
