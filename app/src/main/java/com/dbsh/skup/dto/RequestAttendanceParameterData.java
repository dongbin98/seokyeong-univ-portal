package com.dbsh.skup.dto;

public class RequestAttendanceParameterData {
	/**
	 *  parameter.put("ID", id);
	 * 	parameter.put("LECT_YEAR", year);
	 * 	parameter.put("LECT_TERM", term);
	 * 	parameter.put("STNT_NUMB", id);
	 */
	private String ID;
	private String LECT_YEAR;
	private String LECT_TERM;
	private String STNT_NUMB;

	public RequestAttendanceParameterData(String ID, String LECT_YEAR, String LECT_TERM, String STNT_NUMB) {
		this.ID = ID;
		this.LECT_YEAR = LECT_YEAR;
		this.LECT_TERM = LECT_TERM;
		this.STNT_NUMB = STNT_NUMB;
	}
}
