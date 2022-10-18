package com.dbsh.skup.model;

public class RequestGradeTotalCreditData {
	/**
	 {
	 "path": "common/selectList",
	 "SYS_ID": "AL",
	 "MAP_ID": "education.usc.USC_09001_V.select02",
	 "parameter": {
	 "ID": "2017305067",
	 "STU_NO": "2017305067"
	 },
	 "userID": "2017305067",
	 "programID": "USC_09001_V",
	 "accessToken": "ec6e7311-6ffc-41f3-9cd0-67c888e753c0"
	 }
	 */
	private String MAP_ID;
	private String SYS_ID;
	private String accessToken;
	private String path;
	private String programID;
	private String userID;
	private RequestGradeTotalCreditParameterData parameter;

	public RequestGradeTotalCreditData(String MAP_ID, String SYS_ID, String accessToken, String path, String programID, String userID, RequestGradeTotalCreditParameterData parameter) {
		this.MAP_ID = MAP_ID;
		this.SYS_ID = SYS_ID;
		this.accessToken = accessToken;
		this.path = path;
		this.programID = programID;
		this.userID = userID;
		this.parameter = parameter;
	}
}
