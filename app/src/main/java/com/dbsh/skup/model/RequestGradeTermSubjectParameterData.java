package com.dbsh.skup.model;

public class RequestGradeTermSubjectParameterData {
	/**
	 "parameter": {
	 "ID": "2017305067",
	 "STU_NO": "2017305067"
	 },
	 */
	private String ID;
	private String SCH_YEAR;
	private String SCH_TERM;
	private String STU_NO;

	public RequestGradeTermSubjectParameterData(String ID, String SCH_YEAR, String SCH_TERM, String STU_NO) {
		this.ID = ID;
		this.SCH_YEAR = SCH_YEAR;
		this.SCH_TERM = SCH_TERM;
		this.STU_NO = STU_NO;
	}
}
