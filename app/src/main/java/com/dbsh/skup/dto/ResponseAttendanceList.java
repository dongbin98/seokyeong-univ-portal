package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAttendanceList {
	@SerializedName("SUBJ_CD")      // 학수번호
	@Expose
	private String subjCd;

	@SerializedName("CLSS_NUMB")    // 분반
	@Expose
	private String clssNumb;

	@SerializedName("SUBJ_NM")      // 강의명
	@Expose
	private String subjNm;

	private int attendanceRate;

	private double subjTime;

	public String getSubjCd() {
		return subjCd;
	}

	public String getClssNumb() {
		return clssNumb;
	}

	public String getSubjNm() {
		return subjNm;
	}

	public int getAttendanceRate() {
		return attendanceRate;
	}

	public void setAttendanceRate(int attendanceRate) {
		this.attendanceRate = attendanceRate;
	}

	public double getSubjTime() {
		return subjTime;
	}

	public void setSubjTime(double subjTime) {
		this.subjTime = subjTime;
	}
}
