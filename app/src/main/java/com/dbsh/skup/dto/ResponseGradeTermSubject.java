package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseGradeTermSubject {
	@SerializedName("RTN_STATUS")
	@Expose
	private String rtnStatus;

	@SerializedName("LIST")
	@Expose
	private ArrayList<ResponseGradeTermSubjectList> responseGradeTermSubjectLists;

	public String getRtnStatus() {
		return rtnStatus;
	}

	public void setRtnStatus(String rtnStatus) {
		this.rtnStatus = rtnStatus;
	}

	public ArrayList<ResponseGradeTermSubjectList> getResponseGradeTermSubjectLists() {
		return responseGradeTermSubjectLists;
	}

	public void setResponseGradeTermSubjectLists(ArrayList<ResponseGradeTermSubjectList> responseGradeTermSubjectLists) {
		this.responseGradeTermSubjectLists = responseGradeTermSubjectLists;
	}
}
