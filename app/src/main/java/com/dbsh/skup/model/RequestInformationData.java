package com.dbsh.skup.model;

public class RequestInformationData {
	/**
	 * {
	 *   "path": "common/selectOne",
	 *   "SYS_ID": "AL",
	 *   "MAP_ID": "education.cmn.CMN_01008_T.SELECT",
	 *   "parameter": {
	 *     "JOB_GB": "1",
	 *     "ID": "2017305045",
	 *     "STU_NO": "2017305045"
	 *   },
	 *   "userID": "2017305045",
	 *   "programID": "CMN_01008_T",
	 *   "accessToken": "75455dc6-f365-4282-bfdd-0d1ffa0506fe"
	 * }
	 */
	private String MAP_ID;
	private String SYS_ID;
	private String accessToken;
	private String path;
	private String programID;
	private String userID;
	private RequestInformationParameterData parameter;

	public RequestInformationData(String MAP_ID, String SYS_ID, String accessToken, String path, String programID, String userID, RequestInformationParameterData parameter) {
		this.MAP_ID = MAP_ID;
		this.SYS_ID = SYS_ID;
		this.accessToken = accessToken;
		this.path = path;
		this.programID = programID;
		this.userID = userID;
		this.parameter = parameter;
	}
}
