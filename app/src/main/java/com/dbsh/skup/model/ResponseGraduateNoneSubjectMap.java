package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGraduateNoneSubjectMap {
    @SerializedName("RUS10")
    @Expose
    private String rus10;

    @SerializedName("RUS12")
    @Expose
    private String rus12;

    @SerializedName("RUS14")
    @Expose
    private String rus14;

    @SerializedName("RUS_RESULT")
    @Expose
    private String rusResult;

    public String getRus10() {
        return rus10;
    }

    public String getRus12() {
        return rus12;
    }

    public String getRus14() {
        return rus14;
    }

    public String getRusResult() {
        return rusResult;
    }
}
