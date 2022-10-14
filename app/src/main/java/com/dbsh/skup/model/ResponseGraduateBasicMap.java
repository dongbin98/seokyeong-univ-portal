package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGraduateBasicMap {
    @SerializedName("ACQU_PNT")
    @Expose
    private String acquPnt;

    @SerializedName("GRD_MARK_AVG")
    @Expose
    private String grdMarkAvg;

    @SerializedName("GRD_SCRN_RSLT")
    @Expose
    private String grdScrnRslt;

    public String getAcquPnt() {
        return acquPnt;
    }

    public String getGrdMarkAvg() {
        return grdMarkAvg;
    }

    public String getGrdScrnRslt() {
        return grdScrnRslt;
    }
}
