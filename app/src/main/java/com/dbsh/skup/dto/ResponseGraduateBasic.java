package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGraduateBasic {
    @SerializedName("RTN_STATUS")
    @Expose
    private String rtnStatus;

    @SerializedName("MAP")
    @Expose
    private ResponseGraduateBasicMap responseGraduateBasicMap;

    public String getRtnStatus() {
        return rtnStatus;
    }

    public ResponseGraduateBasicMap getResponseGraduateBasicMap() {
        return responseGraduateBasicMap;
    }
}
