package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseGradeTotalCredit {
	@SerializedName("RTN_STATUS")
	@Expose
	private String rtnStatus;

	@SerializedName("LIST")
	@Expose
	private ArrayList<ResponseGradeTotalCreditList> responseGradeTotalCreditLists;

	public String getRtnStatus() {
		return rtnStatus;
	}

	public void setRtnStatus(String rtnStatus) {
		this.rtnStatus = rtnStatus;
	}

	public ArrayList<ResponseGradeTotalCreditList> getResponseGradeTotalCreditLists() {
		return responseGradeTotalCreditLists;
	}

	public void setResponseGradeTotalCreditLists(ArrayList<ResponseGradeTotalCreditList> responseGradeTotalCreditLists) {
		this.responseGradeTotalCreditLists = responseGradeTotalCreditLists;
	}
}
