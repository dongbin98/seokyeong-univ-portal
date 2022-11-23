package com.dbsh.skup.api;

import com.dbsh.skup.dto.RequestPasswordAuthData;
import com.dbsh.skup.dto.RequestPasswordChangeData;
import com.dbsh.skup.dto.RequestPasswordCheckData;
import com.dbsh.skup.dto.RequestUserData;
import com.dbsh.skup.dto.ResponseLogin;
import com.dbsh.skup.dto.ResponsePassword;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginApi {
	/* Login */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("auth2/login.sku")
	Call<ResponseLogin> getUserData(@Body RequestUserData requestUserData);

	/* Password -> My info */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("auth2/requestAuthCode.sku")
	Call<ResponsePassword> getPasswordAuth(@Body RequestPasswordAuthData requestPasswordAuthData);

	/* Password -> Check Auth */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("auth2/checkAuthCode.sku")
	Call<ResponsePassword> getPasswordCheck(@Body RequestPasswordCheckData requestPasswordCheckData);

	/* Password -> Reset, Change PW */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("auth2/resetUserPW.sku")
	Call<ResponsePassword> getPasswordChange(@Body RequestPasswordChangeData requestPasswordChangeData);
}
