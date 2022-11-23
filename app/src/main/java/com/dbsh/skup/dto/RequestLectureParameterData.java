package com.dbsh.skup.dto;

public class RequestLectureParameterData {
	/**
	 *  parameter.put("ID", id);
	 * 	parameter.put("LECT_YEAR", year);
	 * 	parameter.put("LECT_TERM", term);
	 * 	parameter.put("STU_NO", id);
	 */
	private String ID;
	private String LECT_YEAR;
	private String LECT_TERM;
	private String STU_NO;

	public RequestLectureParameterData(String ID, String LECT_YEAR, String LECT_TERM, String STU_NO) {
		this.ID = ID;
		this.LECT_YEAR = LECT_YEAR;
		this.LECT_TERM = LECT_TERM;
		this.STU_NO = STU_NO;
	}
}
