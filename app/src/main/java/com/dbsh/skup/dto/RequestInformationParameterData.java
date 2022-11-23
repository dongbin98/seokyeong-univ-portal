package com.dbsh.skup.dto;

public class RequestInformationParameterData {
	/**
	 *  "parameter": {
	 *     "JOB_GB": "1",
	 *     "ID": "2017305045",
	 *     "STU_NO": "2017305045"
	 *   },
	 */
	private String JOB_GB;
	private String ID;
	private String STU_NO;

	public RequestInformationParameterData(String JOB_GB, String ID, String STU_NO) {
		this.JOB_GB = JOB_GB;
		this.ID = ID;
		this.STU_NO = STU_NO;
	}
}
