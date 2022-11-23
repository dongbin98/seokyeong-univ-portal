package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUserInfo  {
	/* 	@SerializedName : 매칭되는 이름 명시
		@Expose : 해당 값이 null일 경우 자동 생략
	 */
	@SerializedName("ID")
	@Expose
	private String id;              	// 학번
	@SerializedName("KOR_NAME")
	@Expose
	private String korName;         	// 이름
	@SerializedName("PHONE_MOBILE")
	@Expose
	private String phoneMobile;     	// 전화번호
	@SerializedName("COL_NM")
	@Expose
	private String colNm;         		// 단대
	@SerializedName("TEAM_NM")
	@Expose
	private String teamNm;        		// 학과
	@SerializedName("EMAIL")
	@Expose
	private String email;    			// 이메일주소
	@SerializedName("WEBMAIL_ID")
	@Expose
	private String webmailId;  			// 학교메일주소
	@SerializedName("TUTOR_NAME")
	@Expose
	private String tutorName;       	// 멘토교수
	@SerializedName("SCH_YEAR")
	@Expose
	private String schYear;         	// 현재 년도
	@SerializedName("SCH_TERM")
	@Expose
	private String schTerm;         	// 현재 학기
	@SerializedName("SCHYR")
	@Expose
	private String schyr;           	// 현재 학년
	@SerializedName("SCH_REG_STAT_NM")
	@Expose
	private String schRegStatNm;     	// 재학 휴학 여부(한글)
	@SerializedName("RESI_NO")
	@Expose
	private String resiNo;     	// 생년월일
	@SerializedName("REQ_PW_MOD_DATE")
	@Expose
	private String reqPwModDate;     	// 마지막 비밀번호 변경

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKorName() {
		return korName;
	}

	public String getPhoneMobile() {
		return phoneMobile;
	}

	public String getColNm() {
		return colNm;
	}

	public String getTeamNm() {
		return teamNm;
	}

	public String getEmail() {
		return email;
	}

	public String getWebmailId() {
		return webmailId;
	}

	public String getTutorName() {
		return tutorName;
	}

	public String getSchYear() {
		return schYear;
	}

	public String getSchTerm() {
		return schTerm;
	}

	public String getSchyr() {
		return schyr;
	}

	public String getSchRegStatNm() {
		return schRegStatNm;
	}

	public String getResiNo() { return resiNo; }

	public String getReqPwModDate() {
		return reqPwModDate;
	}
}

