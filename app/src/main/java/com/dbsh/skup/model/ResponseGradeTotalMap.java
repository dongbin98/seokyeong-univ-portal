package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGradeTotalMap {
	@SerializedName("ACQU_PNT")
	@Expose
	private String acquPnt;

	@SerializedName("APPLY_PNT")
	@Expose
	private String applyPnt;

	@SerializedName("GRD_MARK_AVG")
	@Expose
	private String grdMarkAvg;

	@SerializedName("TOT_SCR")
	@Expose
	private String totScr;
}
