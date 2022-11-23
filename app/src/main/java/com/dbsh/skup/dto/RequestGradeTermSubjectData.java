package com.dbsh.skup.dto;

public class RequestGradeTermSubjectData {
	/**
	 {
	 "path": "common/selectList",
	 "SYS_ID": "AL",
	 "MAP_ID": "education.usc.USC_09001_V.select_sub",
	 "parameter": {
	 "SCH_YEAR": "2017",
	 "SCH_TERM": "2",
	 "ID": "2017305067",
	 "STU_NO": "2017305067"
	 },
	 "userID": "2017305067",
	 "programID": "USC_09001_V",
	 "accessToken": "4c0d4e66-5142-41f2-8687-d0798712a0f7"
	 }
	 */
	private String MAP_ID;
	private String SYS_ID;
	private String accessToken;
	private String path;
	private String programID;
	private String userID;
	private RequestGradeTermSubjectParameterData parameter;

	public RequestGradeTermSubjectData(String MAP_ID, String SYS_ID, String accessToken, String path, String programID, String userID, RequestGradeTermSubjectParameterData parameter) {
		this.MAP_ID = MAP_ID;
		this.SYS_ID = SYS_ID;
		this.accessToken = accessToken;
		this.path = path;
		this.programID = programID;
		this.userID = userID;
		this.parameter = parameter;
	}
}
