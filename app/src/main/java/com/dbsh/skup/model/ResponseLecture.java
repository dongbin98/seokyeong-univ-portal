package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseLecture {
	@SerializedName("RTN_STATUS")
	@Expose
	private String rtnStatus;

	@SerializedName("LIST")
	@Expose
	private ArrayList<ResponseLectureList> responseLectureLists;

	public String getRtnStatus() {
		return rtnStatus;
	}

	public void setRtnStatus(String rtnStatus) {
		this.rtnStatus = rtnStatus;
	}

	public ArrayList<ResponseLectureList> getResponseLectureLists() {
		return responseLectureLists;
	}

	public void setResponseLectureLists(ArrayList<ResponseLectureList> lectureLists) {
		this.responseLectureLists = lectureLists;
	}
}
