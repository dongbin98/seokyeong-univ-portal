package com.dbsh.skup.model;

public class RequestLecturePlanWeekData {
	/**
	 * {
	 *   "path": "common/selectList",
	 *   "SYS_ID": "AL",
	 *   "MAP_ID": "education.ucs.UCS_03100_T.SELECT_REPORT_CLASSPLAN",
	 *   "parameter": {
	 *     "LECT_YEAR": "2022",
	 *     "LECT_TERM": "2",
	 *     "SUBJ_CD": "AC1005",
	 *     "CLSS_NUMB": "01",
	 *     "STAF_NO": "20120283"
	 *   },
	 *   "userID": "2017305045",
	 *   "programID": "UCS_03090_P",
	 *   "accessToken": "63dfebf3-2187-43ee-8efb-9c9ac4a3b8b3"
	 * }
	 */

	private String MAP_ID;
	private String SYS_ID;
	private String accessToken;
	private String path;
	private String programID;
	private String userID;
	private RequestLecturePlanWeekParameterData parameter;

	public RequestLecturePlanWeekData(String MAP_ID, String SYS_ID, String accessToken, String path, String programID, String userID, RequestLecturePlanWeekParameterData parameter) {
		this.MAP_ID = MAP_ID;
		this.SYS_ID = SYS_ID;
		this.accessToken = accessToken;
		this.path = path;
		this.programID = programID;
		this.userID = userID;
		this.parameter = parameter;
	}
}
