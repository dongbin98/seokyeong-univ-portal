package com.dbsh.skup.dto;

public class RequestGradeTotalCreditParameterData {
	/**
	 "parameter": {
	 "ID": "2017305067",
	 "STU_NO": "2017305067"
	 },
	 */
	private String ID;
	private String STU_NO;

	public RequestGradeTotalCreditParameterData(String ID, String STU_NO) {
		this.ID = ID;
		this.STU_NO = STU_NO;
	}
}
