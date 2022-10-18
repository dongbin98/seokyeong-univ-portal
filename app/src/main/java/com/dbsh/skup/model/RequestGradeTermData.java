package com.dbsh.skup.model;

public class RequestGradeTermData {
	/**
	 {
	 "path": "common/selectList",
	 "SYS_ID": "AL",
	 "MAP_ID": "education.usc.USC_09001_V.select",
	 "parameter": {
	 "ID": "2017305011",
	 "STU_NO": "2017305011"
	 },
	 "userID": "2017305011",
	 "programID": "USC_09001_V",
	 "accessToken": "03f26a95-e8db-43cc-a8fb-9979930e81eb"
	 }
	 */
	private String MAP_ID;
	private String SYS_ID;
	private String accessToken;
	private String path;
	private String programID;
	private String userID;
	private RequestGradeTermParameterData parameter;

	public RequestGradeTermData(String MAP_ID, String SYS_ID, String accessToken, String path, String programID, String userID, RequestGradeTermParameterData parameter) {
		this.MAP_ID = MAP_ID;
		this.SYS_ID = SYS_ID;
		this.accessToken = accessToken;
		this.path = path;
		this.programID = programID;
		this.userID = userID;
		this.parameter = parameter;
	}
}
