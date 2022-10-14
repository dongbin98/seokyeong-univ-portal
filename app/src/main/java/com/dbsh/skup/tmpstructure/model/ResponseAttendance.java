package com.dbsh.skup.tmpstructure.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseAttendance {
	@SerializedName("RTN_STATUS")
	@Expose
	private String rtnStatus;

	@SerializedName("LIST")
	@Expose
	private ArrayList<ResponseAttendanceList> responseAttendanceLists;

	public String getRtnStatus() {
		return rtnStatus;
	}

	public ArrayList<ResponseAttendanceList> getResponseAttendanceLists() {
		return responseAttendanceLists;
	}
}
