package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseGradeTerm {
	@SerializedName("RTN_STATUS")
	@Expose
	private String rtnStatus;

	@SerializedName("LIST")
	@Expose
	private ArrayList<ResponseGradeTermList> responseGradeTermLists;

	public String getRtnStatus() {
		return rtnStatus;
	}

	public void setRtnStatus(String rtnStatus) {
		this.rtnStatus = rtnStatus;
	}

	public ArrayList<ResponseGradeTermList> getResponseGradeTermLists() {
		return responseGradeTermLists;
	}

	public void setResponseGradeTermLists(ArrayList<ResponseGradeTermList> responseGradeTermLists) {
		this.responseGradeTermLists = responseGradeTermLists;
	}
}
