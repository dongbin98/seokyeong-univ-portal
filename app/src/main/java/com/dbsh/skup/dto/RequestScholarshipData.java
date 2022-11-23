package com.dbsh.skup.dto;

public class RequestScholarshipData {
	/**
	 	payload.put("MAP_ID", "education.uss.USS_01011_T.select_janghak");
	 	payload.put("SYS_ID", "AL");
	 	payload.put("accessToken", token);
	 	payload.put("parameter", parameter);
	 	payload.put("path", "common/selectlist");
	 	payload.put("programID", "USS_02013_V");
	 	payload.put("userID", id);
	 */
	private String MAP_ID;
	private String SYS_ID;
	private String accessToken;
	private String path;
	private String programID;
	private String userID;
	private RequestScholarshipParameterData parameter;

	public RequestScholarshipData(String MAP_ID, String SYS_ID, String accessToken, String path, String programID, String userID, RequestScholarshipParameterData parameter) {
		this.MAP_ID = MAP_ID;
		this.SYS_ID = SYS_ID;
		this.accessToken = accessToken;
		this.path = path;
		this.programID = programID;
		this.userID = userID;
		this.parameter = parameter;
	}
}
