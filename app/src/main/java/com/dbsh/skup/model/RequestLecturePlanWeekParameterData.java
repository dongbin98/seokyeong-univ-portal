package com.dbsh.skup.model;

public class RequestLecturePlanWeekParameterData {
	/**
	 *   "parameter": {
	 *     "LECT_YEAR": "2022",
	 *     "LECT_TERM": "2",
	 *     "SUBJ_CD": "AC1005",
	 *     "CLSS_NUMB": "01",
	 *     "STAF_NO": "20120283"
	 *   },
	 */

	private String LECT_YEAR;
	private String LECT_TERM;
	private String SUBJ_CD;
	private String CLSS_NUMB;
	private String STAF_NO;

	public RequestLecturePlanWeekParameterData(String LECT_YEAR, String LECT_TERM, String SUBJ_CD, String CLSS_NUMB, String STAF_NO) {
		this.LECT_YEAR = LECT_YEAR;
		this.LECT_TERM = LECT_TERM;
		this.SUBJ_CD = SUBJ_CD;
		this.CLSS_NUMB = CLSS_NUMB;
		this.STAF_NO = STAF_NO;
	}
}
