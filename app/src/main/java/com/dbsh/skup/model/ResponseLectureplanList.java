package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLectureplanList {
	@SerializedName("COLE_NM")
	@Expose
	private String coleNm;

	@SerializedName("DEPT_NM")
	@Expose
	private String deptNm;

	@SerializedName("PROF_NM")
	@Expose
	private String profNm;

	@SerializedName("PROF1")
	@Expose
	private String prof1;

	@SerializedName("SUBJ_NM")
	@Expose
	private String subjNm;

	@SerializedName("SUBJ_CD")
	@Expose
	private String subjCd;

	@SerializedName("CLSS_NUMB")
	@Expose
	private String clssNumb;

	@SerializedName("SUBJ_TIME")
	@Expose
	private String subjTime;

	@SerializedName("SUBJ_PONT")
	@Expose
	private String subjPont;

	@SerializedName("SCHL_SHYR")
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
