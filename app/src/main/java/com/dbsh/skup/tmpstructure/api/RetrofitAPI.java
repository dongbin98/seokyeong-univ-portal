package com.dbsh.skup.tmpstructure.api;

import com.dbsh.skup.tmpstructure.model.RequestUserData;
import com.dbsh.skup.tmpstructure.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitAPI {
	/* Login */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("auth2/login.sku")
	Call<ResponseLogin> getUserData(@Body RequestUserData requestUserData);

	/* 그 외 */
	@Headers({"Accept: application/json", "content-type: application/json", "Autorization: Bearer "})
	@POST("common/")
	Call<ResponseLogin> getAnotherData(@Body RequestUserData requestUserData);
}
