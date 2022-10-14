package com.dbsh.skup.api;

import com.dbsh.skup.model.RequestUserData;
import com.dbsh.skup.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginApi {

	String accessToken = "";
	/* Login */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("auth2/login.sku")
	Call<ResponseLogin> getUserData(@Body RequestUserData requestUserData);
}
