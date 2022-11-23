package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLecturePlanWeekList {
	@SerializedName("WEEK_NM")  		// 주차
	@Expose
	private String weekNm;

	@SerializedName("WEEK_TITL01")    	// 타이틀
	@Expose
	private String weekTitl01;

	@SerializedName("LECT_PLAN01")  	// 강의계획 및 내용
	@Expose
	private String lectPlan01;

	@SerializedName("LECT_MTHD01")    	// 강의진행방식
	@Expose
	private String lectMthd01;

	@SerializedName("REPOT_ETC01")    	// 과제물, 시험, 독서
	@Expose
	private String repotEtc01;

	public String getWeekNm() {
		return weekNm;
	}

	public String getWeekTitl01() {
		return weekTitl01;
	}

	public String getLectPlan01() {
		return lectPlan01;
	}

	public String getLectMthd01() {
		return lectMthd01;
	}

	public String getRepotEtc01() {
		return repotEtc01;
	}
}
