package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseLectureplan {
    @SerializedName("RTN_STATUS")
    @Expose
    private String rtnStatus;

    @SerializedName("LIST")
    @Expose
    private ArrayList<ResponseLectureplanList> responseLectureplanLists;

    public String getRtnStatus() {
        return rtnStatus;
    }

    public ArrayList<ResponseLectureplanList> getResponseLectureplanLists() {
        return responseLectureplanLists;
    }
}
