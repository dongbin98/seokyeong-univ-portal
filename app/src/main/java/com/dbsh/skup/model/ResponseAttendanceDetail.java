package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseAttendanceDetail {
	@SerializedName("RTN_STATUS")
	@Expose
	private String rtnStatus;

	@SerializedName("LIST")
	@Expose
	private ArrayList<ResponseAttendanceDetailList> responseAttendanceDetailLists;

	public String getRtnStatus() {
		return rtnStatus;
	}

	public ArrayList<ResponseAttendanceDetailList> getResponseAttendanceDetailLists() {
		return responseAttendanceDetailLists;
	}
}
