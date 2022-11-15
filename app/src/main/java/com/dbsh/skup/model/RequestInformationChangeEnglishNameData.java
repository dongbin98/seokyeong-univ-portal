package com.dbsh.skup.model;

public class RequestInformationChangeEnglishNameData {
	/**
	 * {
	 *   "path": "common/singleProcessing",
	 *   "SYS_ID": "AL",
	 *   "MAP_ID": "education.cmn.CMN_01008_T.UPDATE_USR_MASTER",
	 *   "parameter": {
	 *     "ENG_NM": "Yeom Dong Bin",
	 *     "UPD_ID": "2017305045",
	 *     "UPD_PROG_ID": "CMN_01008_T",
	 *     "UPD_ADDR": "211.234.192.233",
	 *     "ID": "2017305045",
	 *     "STU_NO": "2017305045"
	 *   },
	 *   "userID": "2017305045",
	 *   "programID": "CMN_01008_T",
	 *   "accessToken": "dd154098-1075-4bf8-93d2-8c90276e7cf3"
	 * }
	 */
	private String MAP_ID;
	private String SYS_ID;
	private String accessToken;
	private String path;
	private String programID;
	private String userID;
	private RequestInformationChangeEnglishNameParameterData parameter;

	public RequestInformationChangeEnglishNameData(String MAP_ID, String SYS_ID, String accessToken, String path, String programID, String userID, RequestInformationChangeEnglishNameParameterData parameter) {
		this.MAP_ID = MAP_ID;
		this.SYS_ID = SYS_ID;
		this.accessToken = accessToken;
		this.path = path;
		this.programID = programID;
		this.userID = userID;
		this.parameter = parameter;
	}
}
