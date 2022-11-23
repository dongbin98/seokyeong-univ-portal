package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGradeTotal {
	@SerializedName("RTN_STATUS")
	@Expose
	private String rtnStatus;

	@SerializedName("MAP")
	@Expose
	private ResponseGradeTotalMap responseGradeTotalMap;

	public String getRtnStatus() {
		return rtnStatus;
	}

	public void setRtnStatus(String rtnStatus) {
		this.rtnStatus = rtnStatus;
	}

	public ResponseGradeTotalMap getResponseGradeTotalMap() {
		return responseGradeTotalMap;
	}

	public void setResponseGradeTotalMap(ResponseGradeTotalMap responseGradeTotalMap) {
		this.responseGradeTotalMap = responseGradeTotalMap;
	}
}
