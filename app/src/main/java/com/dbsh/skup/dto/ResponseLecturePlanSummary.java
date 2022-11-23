package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLecturePlanSummary {
	@SerializedName("RTN_STATUS")
	@Expose
	private String rtnStatus;

	@SerializedName("MAP")
	@Expose
	private ResponseLecturePlanSummaryMap responseLecturePlanSummaryMap;

	public String getRtnStatus() {
		return rtnStatus;
	}

	public ResponseLecturePlanSummaryMap getResponseLecturePlanSummaryMap() {
		return responseLecturePlanSummaryMap;
	}
}
