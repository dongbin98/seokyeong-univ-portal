package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGradeTermSubjectList {
	@SerializedName("SUBJ_NO")
	@Expose
	private String subjNo;

	@SerializedName("SUBJ_NM")
	@Expose
	private String subjNm;

	@SerializedName("CMPT_DIV_NM")
	@Expose
	private String cmptDivNm;

	@SerializedName("PNT")
	@Expose
	private String pnt;

	@SerializedName("GRD")
	@Expose
	private String grd;

	@SerializedName("PROF1_NM")
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
