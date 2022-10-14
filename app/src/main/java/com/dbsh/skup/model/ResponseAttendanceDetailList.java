package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAttendanceDetailList {
	@SerializedName("CHECK_DATE_NM")
	@Expose
	private String checkDateNm;

	@SerializedName("WEEK_TIME")
	@Expose
	private String weekTime;

	@SerializedName("ABSN_TIME")
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
