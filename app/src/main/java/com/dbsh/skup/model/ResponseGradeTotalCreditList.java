package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGradeTotalCreditList {
	@SerializedName("PNT_20")
	@Expose
	private String majorTotalPoint;

	@SerializedName("PNT_23")
	@Expose
	private String majorPoint;

	@SerializedName("PNT_26")
	@Expose
	private String majorCorePoint;

	@SerializedName("PNT_27")
	@Expose
	private String majorDeepenPoint;

	@SerializedName("PNT_10")
	@Expose
	private String liberalTotalPoint;

	@SerializedName("PNT_11")
	@Expose
	private String liberalRequirementPoint;

	@SerializedName("PNT_15")
	@Expose
	private String liberalSelectionPoint;

	@SerializedName("PNT_00")
	@Expose
	private String etcTotalPoint;

	@SerializedName("PNT_32")
	@Expose
	private String doubleMajorPint;

	@SerializedName("PNT_33")
	@Expose
	private String normalSelectionPoint;

	@SerializedName("PNT_34")
	@Expose
	private String freeSelectionPoint;

	public String getMajorTotalPoint() {
		return majorTotalPoint;
	}

	public String getMajorPoint() {
		return majorPoint;
	}

	public String getMajorCorePoint() {
		return majorCorePoint;
	}

	public String getMajorDeepenPoint() {
		return majorDeepenPoint;
	}

	public String getLiberalTotalPoint() {
		return liberalTotalPoint;
	}

	public String getLiberalRequirementPoint() {
		return liberalRequirementPoint;
	}

	public String getLiberalSelectionPoint() {
		return liberalSelectionPoint;
	}

	public String getEtcTotalPoint() {
		return etcTotalPoint;
	}

	public String getDoubleMajorPint() {
		return doubleMajorPint;
	}

	public String getNormalSelectionPoint() {
		return normalSelectionPoint;
	}

	public String getFreeSelectionPoint() {
		return freeSelectionPoint;
	}
}
