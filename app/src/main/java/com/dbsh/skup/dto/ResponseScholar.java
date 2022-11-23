package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseScholar {
    @SerializedName("RTN_STATUS")
    @Expose
    private String rtnStatus;

    @SerializedName("LIST")
    @Expose
    private ArrayList<ResponseScholarList> responseScholarLists;

    public String getRtnStatus() {
        return rtnStatus;
    }

    public ArrayList<ResponseScholarList> getResponseScholarLists() {
        return responseScholarLists;
    }
}
