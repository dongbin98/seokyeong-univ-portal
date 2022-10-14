package com.dbsh.skup.tmpstructure.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseTuitionMap {
	@SerializedName("ENT_FEE")
	@Expose
	private String entFee;

	@SerializedName("LESN_FEE")
	@Expose
	private String lesnFee;

	@SerializedName("COOP_DGR_AMT")
	@Expose
	private String coopDgrAmt;

	@SerializedName("USS_ENT_FEE")
	@Expose
	private String ussEntFee;

	@SerializedName("USS_LESN_FEE")
	@Expose
	private String ussLesnFee;

	@SerializedName("SCLS_TOT")
	@Expose
	private String sclsTot;

	@SerializedName("TOT_AMT")
	@Expose
	private String totAmt;

	@SerializedName("REG_AMT")
	@Expose
	private String regAmt;

	@SerializedName("NON_PAY")
	@Expose
	private String nonPay;

	@SerializedName("TEMP_ACCT")
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
