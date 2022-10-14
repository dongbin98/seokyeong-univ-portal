package com.dbsh.skup.tmpstructure.api;

import com.dbsh.skup.tmpstructure.model.RequestAttendanceData;
import com.dbsh.skup.tmpstructure.model.RequestAttendanceDetailData;
import com.dbsh.skup.tmpstructure.model.RequestLectureData;
import com.dbsh.skup.tmpstructure.model.RequestTuitionData;
import com.dbsh.skup.tmpstructure.model.ResponseAttendance;
import com.dbsh.skup.tmpstructure.model.ResponseAttendanceDetail;
import com.dbsh.skup.tmpstructure.model.ResponseLecture;
import com.dbsh.skup.tmpstructure.model.ResponseTuition;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PortalApi {

	/* 강의 리스트 */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("common/selectList.sku")
	Call<ResponseLecture> getLectureData(@Body RequestLectureData requestLectureData);

	/* 등록금 */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("common/selectOne.sku")
	Call<ResponseTuition> getTuitionData(@Body RequestTuitionData requestTuitionData);

	/* 출결 조회 */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("common/selectList.sku")
	Call<ResponseAttendance> getAttendanceData(@Body RequestAttendanceData requestAttendanceData);

	/* 출결 상세조회 */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("common/selectList.sku")
	Call<ResponseAttendanceDetail> getAttendanceDetailData(@Body RequestAttendanceDetailData requestAttendanceDetailData);
}
