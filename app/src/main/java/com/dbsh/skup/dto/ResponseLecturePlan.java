package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseLecturePlan {
    @SerializedName("RTN_STATUS")
    @Expose
    private String rtnStatus;

    @SerializedName("LIST")
    @Expose
    private ArrayList<ResponseLecturePlanList> responseLecturePlanLists;

    public String getRtnStatus() {
        return rtnStatus;
    }

    public ArrayList<ResponseLecturePlanList> getResponseLectureplanLists() {
        return responseLecturePlanLists;
    }
}
