package com.dbsh.skup.tmpstructure.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseLecture {
	@SerializedName("RTN_STATUS")
	@Expose
	private String rtnStatus;

	@SerializedName("LIST")
	@Expose
	private ArrayList<ResponseLectureList> lectureLists = null;

	public String getRtnStatus() {
		return rtnStatus;
	}

	public void setRtnStatus(String rtnStatus) {
		this.rtnStatus = rtnStatus;
	}

	public ArrayList<ResponseLectureList> getLectureLists() {
		return lectureLists;
	}

	public void setLectureLists(ArrayList<ResponseLectureList> lectureLists) {
		this.lectureLists = lectureLists;
	}

	@Override
	public String toString() {
		return "ResponseLecture{" +
				"rtnStatus='" + rtnStatus + '\'' +
				", lectureLists=" + lectureLists +
				'}';
	}
}
