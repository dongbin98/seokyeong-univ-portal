package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseInformationChange {
	@SerializedName("RTN_STATUS")
	@Expose
	private String rtnStatus;

	public String getRtnStatus() {
		return rtnStatus;
	}
}
