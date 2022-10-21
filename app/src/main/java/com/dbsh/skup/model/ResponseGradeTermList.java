package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGradeTermList {
	@SerializedName("ACQU_PNT")     // 취득학점
	@Expose
	private String acquPnt;

	@SerializedName("APPLY_PNT")    // 신청학점
	@Expose
	private String applyPnt;

	@SerializedName("GRD_MARK_AVG") // 평점평균
	@Expose
	private String grdMarkAvg;

	@SerializedName("SCH_RANK")     // 등수
	@Expose
	private String schRank;

	@SerializedName("SCH_YEAR")     // 해당 년도
	@Expose
	private String schYear;

	@SerializedName("SCH_TERM")     // 해당 학기
	@Expose
	private String schTerm;

	public String getAcquPnt() {
		return acquPnt;
	}

	public String getApplyPnt() {
		return applyPnt;
	}

	public String getGrdMarkAvg() {
		return grdMarkAvg;
	}

	public String getSchRank() {
		return schRank;
	}

	public String getSchYear() {
		return schYear;
	}

	public String getSchTerm() {
		return schTerm;
	}
}
