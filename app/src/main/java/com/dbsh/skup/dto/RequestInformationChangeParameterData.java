package com.dbsh.skup.dto;

public class RequestInformationChangeParameterData {
	/**
	 * {
	 *   "ZIPCODE1": "31",
	 *   "ZIPCODE2": "525",
	 *   "ADDR1": "충남 아산시 모종로 21 (모종동, 모종1차한성필하우스)",
	 *   "ADDR2": "102동 702호",
	 *   "TEL_NO1": "041",
	 *   "TEL_NO2": "533",
	 *   "TEL_NO3": "7305",
	 *   "EMAIL": "yeomdongbin@naver.com",
	 *   "HP_NO1": "010",
	 *   "HP_NO2": "4049",
	 *   "HP_NO3": "7305",
	 *   "GURD1_HP_NO1": "010",
	 *   "GURD1_HP_NO2": "3060",
	 *   "GURD1_HP_NO3": "7305",
	 *   "NEW_ZIPCODE1": "31",
	 *   "NEW_ZIPCODE2": "525",
	 *   "GUNMUL_NO": "4420011300106170000045420",
	 *   "NEW_ADDR1": "충남 아산시 모종로 21 (모종동, 모종1차한성필하우스)",
	 *   "NEW_ADDR2": "102동 702호",
	 *   "NEW_ZIPCODE": "31525",
	 *   "NEW_SILJE_ZIPCODE": null,
	 *   "NEW_SILJE_ADDR1": "",
	 *   "NEW_SILJE_ADDR2": "",
	 *   "UPD_ID": "2017305045",
	 *   "UPD_PROG_ID": "CMN_01008_T",
	 *   "UPD_ADDR": "210.110.39.209",
	 *   "ID": "2017305045",
	 *   "STU_NO": "2017305045"
	 * }
	 */
	private String ADDR1;
	private String ADDR2;
	private String EMAIL;
	private String GUNMUL_NO;
	private String GURD1_HP_NO1;
	private String GURD1_HP_NO2;
	private String GURD1_HP_NO3;
	private String HP_NO1;
	private String HP_NO2;
	private String HP_NO3;
	private String ID;
	private String NEW_ADDR1;
	private String NEW_ADDR2;
	private final String NEW_SILJE_ADDR1 = "";
	private final String NEW_SILJE_ADDR2 = "";
	private final String NEW_SILJE_ZIPCODE = null;
	private String NEW_ZIPCODE;
	private String NEW_ZIPCODE1;
	private String NEW_ZIPCODE2;
	private String STU_NO;
	private String TEL_NO1;
	private String TEL_NO2;
	private String TEL_NO3;
	private String UPD_ADDR;
	private String UPD_ID;
	private String UPD_PROG_ID;
	private String ZIPCODE1;
	private String ZIPCODE2;

	public RequestInformationChangeParameterData(String ADDR1, String ADDR2, String EMAIL, String GUNMUL_NO, String GURD1_HP_NO1, String GURD1_HP_NO2, String GURD1_HP_NO3, String HP_NO1, String HP_NO2, String HP_NO3, String ID, String NEW_ADDR1, String NEW_ADDR2, String NEW_ZIPCODE, String NEW_ZIPCODE1, String NEW_ZIPCODE2, String STU_NO, String TEL_NO1, String TEL_NO2, String TEL_NO3, String UPD_ADDR, String UPD_ID, String UPD_PROG_ID, String ZIPCODE1, String ZIPCODE2) {
		this.ADDR1 = ADDR1;
		this.ADDR2 = ADDR2;
		this.EMAIL = EMAIL;
		this.GUNMUL_NO = GUNMUL_NO;
		this.GURD1_HP_NO1 = GURD1_HP_NO1;
		this.GURD1_HP_NO2 = GURD1_HP_NO2;
		this.GURD1_HP_NO3 = GURD1_HP_NO3;
		this.HP_NO1 = HP_NO1;
		this.HP_NO2 = HP_NO2;
		this.HP_NO3 = HP_NO3;
		this.ID = ID;
		this.NEW_ADDR1 = NEW_ADDR1;
		this.NEW_ADDR2 = NEW_ADDR2;
		this.NEW_ZIPCODE = NEW_ZIPCODE;
		this.NEW_ZIPCODE1 = NEW_ZIPCODE1;
		this.NEW_ZIPCODE2 = NEW_ZIPCODE2;
		this.STU_NO = STU_NO;
		this.TEL_NO1 = TEL_NO1;
		this.TEL_NO2 = TEL_NO2;
		this.TEL_NO3 = TEL_NO3;
		this.UPD_ADDR = UPD_ADDR;
		this.UPD_ID = UPD_ID;
		this.UPD_PROG_ID = UPD_PROG_ID;
		this.ZIPCODE1 = ZIPCODE1;
		this.ZIPCODE2 = ZIPCODE2;
	}
}
