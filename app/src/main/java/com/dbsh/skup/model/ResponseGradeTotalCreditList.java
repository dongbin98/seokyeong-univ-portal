package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGradeTotalCreditList {
	@SerializedName("PNT_20")
	@Expose
	private String majorPoint;

	@SerializedName("PNT_10")
	@Expose
	private String liberalPoint;

	@SerializedName("PNT_00")
	@Expose
	private String etcPoint;

	public String getMajorPoint() {
		return majorPoint;
	}

	public String getLiberalPoint() {
		return liberalPoint;
	}

	public String getEtcPoint() {
		return etcPoint;
	}
}
