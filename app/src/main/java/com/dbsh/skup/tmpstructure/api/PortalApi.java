package com.dbsh.skup.tmpstructure.api;

import com.dbsh.skup.tmpstructure.model.RequestLectureData;
import com.dbsh.skup.tmpstructure.model.ResponseLecture;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PortalApi {

	/* 강의 리스트 */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("common/selectList.sku")
	Call<ResponseLecture> getLectureData(@Body RequestLectureData requestLectureData);
}
