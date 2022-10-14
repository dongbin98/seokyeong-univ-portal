package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGraduateSubjectList {
    @SerializedName("AREA_NM")
    @Expose
    private String areaNm;

    @SerializedName("DIS_DATA")
    @Expose
    private String disData;

    @SerializedName("ISU_NAME")
    @Expose
    private String isuName;

    @SerializedName("SUBJ_NM")
    @Expose
    private String subjNm;

    public String getAreaNm() {
        return areaNm;
    }

    public String getDisData() {
        return disData;
    }

    public String getIsuName() {
        return isuName;
    }

    public String getSubjNm() {
        return subjNm;
    }
}
