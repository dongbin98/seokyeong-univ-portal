package com.dbsh.skup.tmpstructure.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseTuition {
	@SerializedName("RTN_STATUS")
	@Expose
	private String rtnStatus;

	@SerializedName("MAP")
	@Expose
	private ResponseTuitionMap map;

	public String getRtnStatus() {
		return rtnStatus;
	}

	public ResponseTuitionMap getMap() {
		return map;
	}
}
