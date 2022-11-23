package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGraduateNoneSubject {
    @SerializedName("RTN_STATUS")
    @Expose
    private String rtnStatus;

    @SerializedName("MAP")
    @Expose
    private ResponseGraduateNoneSubjectMap responseGraduateNoneSubjectMap;

    public String getRtnStatus() {
        return rtnStatus;
    }

    public ResponseGraduateNoneSubjectMap getResponseGraduateNoneSubjectMap() {
        return responseGraduateNoneSubjectMap;
    }
}
