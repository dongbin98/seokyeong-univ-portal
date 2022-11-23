package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGradeTotalCreditList {
	@SerializedName("PNT_20")       // 전공 이수학점 합계
	@Expose
	private String majorTotalPoint;

	@SerializedName("PNT_23")       // 전공 이수학점
	@Expose
	private String majorPoint;

	@SerializedName("PNT_26")       // 전공핵심 이수학점
	@Expose
	private String majorCorePoint;

	@SerializedName("PNT_27")       // 전공심화 이수학점
	@Expose
	private String majorDeepenPoint;

	@SerializedName("PNT_10")       // 교양 이수학점 합계
	@Expose
	private String liberalTotalPoint;

	@SerializedName("PNT_11")       // 교양필수 이수학점
	@Expose
	private String liberalRequirementPoint;

	@SerializedName("PNT_15")       // 교양선택 이수학점
	@Expose
	private String liberalSelectionPoint;

	@SerializedName("PNT_00")       // 기타 이수학점 합계
	@Expose
	private String etcTotalPoint;

	@SerializedName("PNT_32")       // 복수전공 이수학점
	@Expose
	private String doubleMajorPint;

	@SerializedName("PNT_33")       // 일반선택 이수학점
	@Expose
	private String normalSelectionPoint;

	@SerializedName("PNT_34")       // 자유선택 이수학점
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
