package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseScholarList {
    @SerializedName("SCLS_NM")      // 장학명
    @Expose
    private String sclsNm;

    @SerializedName("REMK_TEXT")    // 비고
    @Expose
    private String remkText;

    @SerializedName("SCLS_AMT")     // 금액
    @Expose
    private String sclsAmt;

    public String getSclsNm() {
        return sclsNm;
    }

    public String getRemkText() {
        return remkText;
    }

    public String getSclsAmt() {
        return sclsAmt;
    }
}
