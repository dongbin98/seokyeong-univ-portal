package com.dbsh.skup.api;

import com.dbsh.skup.model.RequestAttendanceData;
import com.dbsh.skup.model.RequestAttendanceDetailData;
import com.dbsh.skup.model.RequestGraduateBasicData;
import com.dbsh.skup.model.RequestGraduateNoneSubjectData;
import com.dbsh.skup.model.RequestGraduateSubjectData;
import com.dbsh.skup.model.RequestLectureData;
import com.dbsh.skup.model.RequestLectureplanData;
import com.dbsh.skup.model.RequestScholarshipData;
import com.dbsh.skup.model.RequestTuitionData;
import com.dbsh.skup.model.ResponseAttendance;
import com.dbsh.skup.model.ResponseAttendanceDetail;
import com.dbsh.skup.model.ResponseGraduateBasic;
import com.dbsh.skup.model.ResponseGraduateNoneSubject;
import com.dbsh.skup.model.ResponseGraduateSubject;
import com.dbsh.skup.model.ResponseLecture;
import com.dbsh.skup.model.ResponseLectureplan;
import com.dbsh.skup.model.ResponseScholar;
import com.dbsh.skup.model.ResponseTuition;

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

	/* 장학 조회 */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("common/selectList.sku")
	Call<ResponseScholar> getScholarshipData(@Body RequestScholarshipData requestScholarshipData);

	/* 졸업이수학점 기준조회 -> 기본 정보 */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("common/selectOne.sku")
	Call<ResponseGraduateBasic> getGraduateBasicData(@Body RequestGraduateBasicData requestGraduateBasicData);

	/* 졸업요건 취득현황 -> 교과영역 이수 확인 */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("common/selectList.sku")
	Call<ResponseGraduateSubject> getGraduateSubjectData(@Body RequestGraduateSubjectData requestGraduateSubjectData);

	/* 졸업요건 취득현황 -> 비교과인증 이수 확인 */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("common/selectOne.sku")
	Call<ResponseGraduateNoneSubject> getGraduateNoneSubjectData(@Body RequestGraduateNoneSubjectData requestGraduateNoneSubjectData);

	/* 강의계획서 */
	@Headers({"Accept: application/json", "content-type: application/json"})
	@POST("common/selectList.sku")
	Call<ResponseLectureplan> getLectureplanData(@Body RequestLectureplanData requestLectureplanData);
}
