package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGradeTermList {
	@SerializedName("ACQU_PNT")
	@Expose
	private String acquPnt;

	@SerializedName("APPLY_PNT")
	@Expose
	private String applyPnt;

	@SerializedName("GRD_MARK_AVG")
	@Expose
	private String grdMarkAvg;

	@SerializedName("SCH_RANK")
	@Expose
	private String schRank;

	@SerializedName("SCH_YEAR")
	@Expose
	private String schYear;

	@SerializedName("SCH_TERM")
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
