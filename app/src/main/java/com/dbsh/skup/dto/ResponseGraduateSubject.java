package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseGraduateSubject {
    @SerializedName("RTN_STATUS")
    @Expose
    private String rtnStatus;

    @SerializedName("LIST")
    @Expose
    private ArrayList<ResponseGraduateSubjectList> responseGraduateSubjectLists;

    public String getRtnStatus() {
        return rtnStatus;
    }

    public ArrayList<ResponseGraduateSubjectList> getResponseGraduateSubjectLists() {
        return responseGraduateSubjectLists;
    }
}
