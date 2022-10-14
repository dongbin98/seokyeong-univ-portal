package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseLogin {
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("USER_INFO")
    @Expose
    private ResponseUserInfo userInfo;
    @SerializedName("RTN_STATUS")
    @Expose
    private String rtnStatus;
    @SerializedName("YEAR_LIST")
    @Expose
    private ArrayList<ResponseYearList> yearList = null;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public ResponseUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ResponseUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getRtnStatus() {
        return rtnStatus;
    }

    public void setRtnStatus(String rtnStatus) {
        this.rtnStatus = rtnStatus;
    }

    public ArrayList<ResponseYearList> getYearList() {
        return yearList;
    }

    public void setYearList(ArrayList<ResponseYearList> yearList) {
        this.yearList = yearList;
    }

    @Override
    public String toString() {
        return "ResponseLogin{" +
                "accessToken='" + accessToken + '\'' +
                ", userInfo=" + userInfo +
                ", rtnStatus='" + rtnStatus + '\'' +
                ", yearList=" + yearList +
                '}';
    }
}
