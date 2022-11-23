package com.dbsh.skup.dto;

public class RequestInformationChangeEnglishNameParameterData {
	/**
	 * {
	 *   "ENG_NM": "Yeom Dong Bin",
	 *   "UPD_ID": "2017305045",
	 *   "UPD_PROG_ID": "CMN_01008_T",
	 *   "UPD_ADDR": "211.234.192.233",
	 *   "ID": "2017305045",
	 *   "STU_NO": "2017305045"
	 * }
	 */
	private String ENG_NM;
	private String UPD_ID;
	private String UPD_PROG_ID;
	private String UPD_ADDR;
	private String ID;
	private String STU_NO;

	public RequestInformationChangeEnglishNameParameterData(String ENG_NM, String UPD_ID, String UPD_PROG_ID, String UPD_ADDR, String ID, String STU_NO) {
		this.ENG_NM = ENG_NM;
		this.UPD_ID = UPD_ID;
		this.UPD_PROG_ID = UPD_PROG_ID;
		this.UPD_ADDR = UPD_ADDR;
		this.ID = ID;
		this.STU_NO = STU_NO;
	}
}
