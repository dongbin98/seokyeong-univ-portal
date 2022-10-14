package com.dbsh.skup.tmpstructure.model;

public class RequestTuitionParameterData {
	/**
	 *  parameter.put("ID", id);
	 * 	parameter.put("SCH_YEAR", year);
	 * 	parameter.put("SCH_TERM", term);
	 * 	parameter.put("STU_NO", id);
	 */

	private String ID;
	private String SCH_YEAR;
	private String SCH_TERM;
	private String STU_NO;

	public RequestTuitionParameterData(String ID, String SCH_YEAR, String SCH_TERM, String STU_NO) {
		this.ID = ID;
		this.SCH_YEAR = SCH_YEAR;
		this.SCH_TERM = SCH_TERM;
		this.STU_NO = STU_NO;
	}
}
