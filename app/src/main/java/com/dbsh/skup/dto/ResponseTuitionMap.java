package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseTuitionMap {
	@SerializedName("ENT_FEE")      // 입학금
	@Expose
	private String entFee;

	@SerializedName("LESN_FEE")     // 수업료
	@Expose
	private String lesnFee;

	@SerializedName("COOP_DGR_AMT") // 공동학위
	@Expose
	private String coopDgrAmt;    

	@SerializedName("USS_ENT_FEE")  // 장학입학금
	@Expose
	private String ussEntFee;

	@SerializedName("USS_LESN_FEE") // 장학수업료
	@Expose
	private String ussLesnFee;

	@SerializedName("SCLS_TOT")     // 장학금 합계
	@Expose
	private String sclsTot;

	@SerializedName("TOT_AMT")      // 등록금합계
	@Expose
	private String totAmt;

	@SerializedName("REG_AMT")      // 납부금액
	@Expose
	private String regAmt;

	@SerializedName("NON_PAY")      // 미납금액
	@Expose
	private String nonPay;

	@SerializedName("TEMP_ACCT")    // 가상계좌
	@Expose
	private String tempAcct;

	public String getEntFee() {
		return entFee;
	}

	public String getLesnFee() {
		return lesnFee;
	}

	public String getCoopDgrAmt() {
		return coopDgrAmt;
	}

	public String getUssEntFee() {
		return ussEntFee;
	}

	public String getUssLesnFee() {
		return ussLesnFee;
	}

	public String getSclsTot() {
		return sclsTot;
	}

	public String getTotAmt() {
		return totAmt;
	}

	public String getRegAmt() {
		return regAmt;
	}

	public String getNonPay() {
		return nonPay;
	}

	public String getTempAcct() {
		return tempAcct;
	}
}
