package com.dbsh.skup.model;

public class RequestLectureData {
	/**
	 * 	payload.put("MAP_ID", "education.ucs.UCS_common.SELECT_TIMETABLE_2018");
	 * 	payload.put("SYS_ID", "AL");
	 * 	payload.put("accessToken", token);
	 * 	payload.put("parameter", parameter);
	 * 	payload.put("path", "common/selectList");
	 * 	payload.put("programID", "UAL_03333_T");
	 * 	payload.put("userID", id);
	 */
	private String MAP_ID;
	private String SYS_ID;
	private String accessToken;
	private String path;
	private String programID;
	private String userID;
	private RequestLectureParameterData parameter;

	public RequestLectureData(String MAP_ID, String SYS_ID, String accessToken, String path, String programID, String userID, RequestLectureParameterData parameter) {
		this.MAP_ID = MAP_ID;
		this.SYS_ID = SYS_ID;
		this.accessToken = accessToken;
		this.path = path;
		this.programID = programID;
		this.userID = userID;
		this.parameter = parameter;
	}
}
