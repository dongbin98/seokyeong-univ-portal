package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAttendanceDetailList {
	@SerializedName("CHECK_DATE_NM")    // 해당 날짜
	@Expose
	private String checkDateNm;

	@SerializedName("WEEK_TIME")        // 요일
	@Expose
	private String weekTime;

	@SerializedName("ABSN_TIME")        // 결석시간
	@Expose
	private String absnTime;

	public String getCheckDateNm() {
		return checkDateNm;
	}

	public String getWeekTime() {
		return weekTime;
	}

	public String getAbsnTime() {
		return absnTime;
	}

}
