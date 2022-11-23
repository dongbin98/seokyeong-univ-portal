package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseInformationMap {
	// 기본정보
	@SerializedName("CONDITIONED_GRADE")    // 수강신청 학년
	@Expose
	private String conditionedGrade;

	// 주소
	@SerializedName("NEWZIPCODE")    // 우편번호
	@Expose
	private String newZipCode;

	@SerializedName("ZIPCODE1")    // 우편번호(구)
	@Expose
	private String zipCode1;

	@SerializedName("ZIPCODE2")    // 우편번호(구)
	@Expose
	private String zipCode2;

	@SerializedName("JUMIN_DORO_ADDR1")    // 주소
	@Expose
	private String juminDoroAddr1;

	@SerializedName("JUMIN_DORO_ADDR2")    // 상세주소
	@Expose
	private String juminDoroAddr2;

	@SerializedName("GUNMUL_NO")    // 건물번호
	@Expose
	private String gunmulNo;

	// 연락처
	@SerializedName("TEL_NO1")    // 전화번호(HOME) 앞의 자리
	@Expose
	private String telNo1;

	@SerializedName("TEL_NO2")    // 전화번호(HOME) 중간 자리
	@Expose
	private String telNo2;

	@SerializedName("TEL_NO3")    // 전화번호(HOME) 끝의 자리
	@Expose
	private String telNo3;

	@SerializedName("HP_NO1")    // 휴대폰번호 앞의 자리
	@Expose
	private String hpNo1;

	@SerializedName("HP_NO2")    // 휴대폰번호 중간 자리
	@Expose
	private String hpNo2;

	@SerializedName("HP_NO3")    // 휴대폰번호 끝의 자리
	@Expose
	private String hpNo3;

	@SerializedName("GURD1_HP_NO1")    // 보호자 휴대폰번호 앞의 자리
	@Expose
	private String gurd1HpNo1;

	@SerializedName("GURD1_HP_NO2")    // 보호자 휴대폰번호 중간 자리
	@Expose
	private String gurd1HpNo2;

	@SerializedName("GURD1_HP_NO3")    // 보호자 휴대폰번호 끝의 자리리
	@Expose
	private String gurd1HpNo3;

	@SerializedName("EMAIL")    // 이메일
	@Expose
	private String email;

	// 기타
	@SerializedName("ENG_NM")    // 영문이름
	@Expose
	private String engNm;

	public String getConditionedGrade() {
		return conditionedGrade;
	}

	public String getNewZipCode() {
		return newZipCode;
	}

	public String getZipCode1() {
		return zipCode1;
	}

	public String getZipCode2() {
		return zipCode2;
	}

	public String getJuminDoroAddr1() {
		return juminDoroAddr1;
	}

	public String getJuminDoroAddr2() {
		return juminDoroAddr2;
	}

	public String getGunmulNo() {
		return gunmulNo;
	}

	public String getTelNo1() {
		return telNo1;
	}

	public String getTelNo2() {
		return telNo2;
	}

	public String getTelNo3() {
		return telNo3;
	}

	public String getHpNo1() {
		return hpNo1;
	}

	public String getHpNo2() {
		return hpNo2;
	}

	public String getHpNo3() {
		return hpNo3;
	}

	public String getGurd1HpNo1() {
		return gurd1HpNo1;
	}

	public String getGurd1HpNo2() {
		return gurd1HpNo2;
	}

	public String getGurd1HpNo3() {
		return gurd1HpNo3;
	}

	public String getEmail() {
		return email;
	}

	public String getEngNm() {
		return engNm;
	}
}
