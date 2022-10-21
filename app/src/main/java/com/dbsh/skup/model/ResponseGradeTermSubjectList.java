package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGradeTermSubjectList {
	@SerializedName("SUBJ_NO")          // 학수번호
	@Expose
	private String subjNo;

	@SerializedName("SUBJ_NM")          // 강의명
	@Expose
	private String subjNm;

	@SerializedName("CMPT_DIV_NM")      // 전공 교양 구분
	@Expose
	private String cmptDivNm;

	@SerializedName("PNT")              // 학점
	@Expose
	private String pnt;

	@SerializedName("GRD")              // 성적 ex) A+
	@Expose
	private String grd;

	@SerializedName("PROF1_NM")         // 교수명
	@Expose
	private String prof1Nm;

	public String getSubjNo() {
		return subjNo;
	}

	public String getSubjNm() {
		return subjNm;
	}

	public String getCmptDivNm() {
		return cmptDivNm;
	}

	public String getPnt() {
		return pnt;
	}

	public String getGrd() {
		return grd;
	}

	public String getProf1Nm() {
		return prof1Nm;
	}
}
