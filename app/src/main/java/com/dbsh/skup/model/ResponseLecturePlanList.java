package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLecturePlanList {
	@SerializedName("COLE_NM")  // 단대명
	@Expose
	private String coleNm;

	@SerializedName("DEPT_NM")  // 학과명
	@Expose
	private String deptNm;

	@SerializedName("PROF_NM")  // 교수명
	@Expose
	private String profNm;

	@SerializedName("PROF1")    // 교수아이디
	@Expose
	private String prof1;

	@SerializedName("SUBJ_NM")  // 강의명
	@Expose
	private String subjNm;

	@SerializedName("SUBJ_CD")  // 학수번호
	@Expose
	private String subjCd;

	@SerializedName("CLSS_NUMB")// 분반
	@Expose
	private String clssNumb;

	@SerializedName("SUBJ_TIME")// 강의시간
	@Expose
	private String subjTime;

	@SerializedName("SUBJ_PONT")// 강의학점
	@Expose
	private String subjPont;

	@SerializedName("SCHL_SHYR")// 대상학년
	@Expose
	private String schlShyr;

	public String getColeNm() {
		return coleNm;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public String getProfNm() {
		return profNm;
	}

	public String getProf1() {
		return prof1;
	}

	public String getSubjNm() {
		return subjNm;
	}

	public String getSubjCd() {
		return subjCd;
	}

	public String getClssNumb() {
		return clssNumb;
	}

	public String getSubjTime() {
		return subjTime;
	}

	public String getSubjPont() {
		return subjPont;
	}

	public String getSchlShyr() {
		return schlShyr;
	}
}
