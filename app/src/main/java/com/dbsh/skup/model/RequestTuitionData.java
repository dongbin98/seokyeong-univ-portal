package com.dbsh.skup.model;

public class RequestTuitionData {
	/**
	 * payload.put("MAP_ID", "education.urg.URG_02012_V.SELECT");
	 * payload.put("SYS_ID", "AL");
	 * payload.put("accessToken", token);
	 * payload.put("parameter", parameter);
	 * payload.put("path", "common/selectOne");
	 * payload.put("programID", "URG_02012_V");
	 * payload.put("userID", id);
	 */

	private String MAP_ID;
	private String SYS_ID;
	private String accessToken;
	private String path;
	private String programID;
	private String userID;
	private RequestTuitionParameterData parameter;

	public RequestTuitionData(String MAP_ID, String SYS_ID, String accessToken, String path, String programID, String userID, RequestTuitionParameterData parameter) {
		this.MAP_ID = MAP_ID;
		this.SYS_ID = SYS_ID;
		this.accessToken = accessToken;
		this.path = path;
		this.programID = programID;
		this.userID = userID;
		this.parameter = parameter;
	}
}
