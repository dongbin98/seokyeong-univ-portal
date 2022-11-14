package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseInformation {
	@SerializedName("RTN_STATUS")
	@Expose
	private String rtnStatus;

	@SerializedName("MAP")
	@Expose
	private ResponseInformationMap responseLecturePlanSummaryMap;

	public String getRtnStatus() {
		return rtnStatus;
	}

	public ResponseInformationMap getResponseLecturePlanSummaryMap() {
		return responseLecturePlanSummaryMap;
	}
}
