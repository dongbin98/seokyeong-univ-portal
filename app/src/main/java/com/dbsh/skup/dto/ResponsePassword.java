package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePassword {
    /**
     * {
     *   "RTN_STATUS": "S",
     *   "RTN_MESSAGE": "1건 정상 처리 되었습니다."
     * }
     */
    @SerializedName("RTN_STATUS")
    @Expose
    private String rtnStatus;

    @SerializedName("RTN_MESSAGE")
    @Expose
    private String RTN_MESSAGE;

    public String getRtnStatus() {
        return rtnStatus;
    }

    public String getRTN_MESSAGE() {
        return RTN_MESSAGE;
    }
}
