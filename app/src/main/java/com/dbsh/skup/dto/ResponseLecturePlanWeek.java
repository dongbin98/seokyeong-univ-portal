package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseLecturePlanWeek {
	@SerializedName("RTN_STATUS")
	@Expose
	private String rtnStatus;

	@SerializedName("LIST")
	@Expose
	private ArrayList<ResponseLecturePlanWeekList> responseLecturePlanWeekLists;

	public String getRtnStatus() {
		return rtnStatus;
	}

	public ArrayList<ResponseLecturePlanWeekList> getResponseLecturePlanWeekLists() {
		return responseLecturePlanWeekLists;
	}
}
