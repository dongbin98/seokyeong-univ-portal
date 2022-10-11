package com.dbsh.skup;

import com.dbsh.skup.tmpstructure.model.Login;

import retrofit2.Call;
import retrofit2.http.POST;

public interface RetrofitAPI {
	@POST("auth2/login.sku")
	Call<Login> getLoginData();

	@POST("common/selectList.sku")
	Call<Login> getAttendanceData();

	@POST("common/selectList.sku")
	Call<Login> getAttendanceDetailData();
}
