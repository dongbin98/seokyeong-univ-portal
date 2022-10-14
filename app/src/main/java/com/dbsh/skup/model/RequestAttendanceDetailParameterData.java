package com.dbsh.skup.model;

public class RequestAttendanceDetailParameterData {
	/**
	 *  parameter.put("CLSS_NUMB", NUMB);
	 * 	parameter.put("LECT_YEAR", year);
	 * 	parameter.put("LECT_TERM", term);
	 * 	parameter.put("STNT_NUMB", id);
	 * 	parameter.put("SUBJ_CD", CD);
	 */
	private String CLSS_NUMB;
	private String LECT_YEAR;
	private String LECT_TERM;
	private String STNT_NUMB;
	private String SUBJ_CD;

	public RequestAttendanceDetailParameterData(String CLSS_NUMB, String LECT_YEAR, String LECT_TERM, String STNT_NUMB, String SUBJ_CD) {
		this.CLSS_NUMB = CLSS_NUMB;
		this.LECT_YEAR = LECT_YEAR;
		this.LECT_TERM = LECT_TERM;
		this.STNT_NUMB = STNT_NUMB;
		this.SUBJ_CD = SUBJ_CD;
	}
}
