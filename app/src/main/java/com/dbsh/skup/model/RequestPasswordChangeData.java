package com.dbsh.skup.model;

public class RequestPasswordChangeData {
    /**
     * {
     *   "ID": "2017305045",
     *   "PW": "",
     *   "CHECK_PW": "",
     *   "AUTH_CODE": "6362",
     *   "userType": "sku"
     * }
     */
    private String ID;
    private String PW;
    private String CHECK_PW;
    private String AUTH_CODE;
    private String userType;

    public RequestPasswordChangeData(String ID, String PW, String CHECK_PW, String AUTH_CODE, String userType) {
        this.ID = ID;
        this.PW = PW;
        this.CHECK_PW = CHECK_PW;
        this.AUTH_CODE = AUTH_CODE;
        this.userType = userType;
    }
}
