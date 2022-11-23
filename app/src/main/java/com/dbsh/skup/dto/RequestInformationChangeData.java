package com.dbsh.skup.dto;

public class RequestInformationChangeData {
	/**
	 * {
	 *   "path": "common/singleProcessing",
	 *   "SYS_ID": "AL",
	 *   "MAP_ID": "education.cmn.CMN_01008_T.UPDATE_USR_PERSONAL_MAT_INFO",
	 *   "parameter": {
	 *     "ZIPCODE1": "31",
	 *     "ZIPCODE2": "525",
	 *     "ADDR1": "충남 아산시 모종로 21 (모종동, 모종1차한성필하우스)",
	 *     "ADDR2": "102동 702호",
	 *     "TEL_NO1": "041",
	 *     "TEL_NO2": "533",
	 *     "TEL_NO3": "7305",
	 *     "EMAIL": "yeomdongbin@naver.com",
	 *     "HP_NO1": "010",
	 *     "HP_NO2": "4049",
	 *     "HP_NO3": "7305",
	 *     "GURD1_HP_NO1": "010",
	 *     "GURD1_HP_NO2": "3060",
	 *     "GURD1_HP_NO3": "7305",
	 *     "NEW_ZIPCODE1": "31",
	 *     "NEW_ZIPCODE2": "525",
	 *     "GUNMUL_NO": "4420011300106170000045420",
	 *     "NEW_ADDR1": "충남 아산시 모종로 21 (모종동, 모종1차한성필하우스)",
	 *     "NEW_ADDR2": "102동 702호",
	 *     "NEW_ZIPCODE": "31525",
	 *     "NEW_SILJE_ZIPCODE": null,
	 *     "NEW_SILJE_ADDR1": "",
	 *     "NEW_SILJE_ADDR2": "",
	 *     "UPD_ID": "2017305045",
	 *     "UPD_PROG_ID": "CMN_01008_T",
	 *     "UPD_ADDR": "210.110.39.209",
	 *     "ID": "2017305045",
	 *     "STU_NO": "2017305045"
	 *   },
	 *   "userID": "2017305045",
	 *   "programID": "CMN_01008_T",
	 *   "accessToken": "f5f0c70e-5f80-4255-ac23-425b1c701202"
	 * }
	 */
	private String MAP_ID;
	private String SYS_ID;
	private String accessToken;
	private String path;
	private String programID;
	private String userID;
	private RequestInformationChangeParameterData parameter;

	public RequestInformationChangeData(String MAP_ID, String SYS_ID, String accessToken, String path, String programID, String userID, RequestInformationChangeParameterData parameter) {
		this.MAP_ID = MAP_ID;
		this.SYS_ID = SYS_ID;
		this.accessToken = accessToken;
		this.path = path;
		this.programID = programID;
		this.userID = userID;
		this.parameter = parameter;
	}
}
