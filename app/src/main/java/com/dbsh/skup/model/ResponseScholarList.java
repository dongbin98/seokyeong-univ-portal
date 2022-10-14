package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseScholarList {
    @SerializedName("SCLS_NM")
    @Expose
    private String sclsNm;

    @SerializedName("REMK_TEXT")
    @Expose
    private String remkText;

    @SerializedName("SCLS_AMT")
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
