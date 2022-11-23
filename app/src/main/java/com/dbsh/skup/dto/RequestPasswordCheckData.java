package com.dbsh.skup.dto;

public class RequestPasswordCheckData {
    /**
     * {
     *   "ID": "2017305045",
     *   "AUTH_CODE": "6362",
     *   "userType": "sku"
     * }
     */
    private String ID;
    private String AUTH_CODE;
    private String userType;

    public RequestPasswordCheckData(String ID, String AUTH_CODE, String userType) {
        this.ID = ID;
        this.AUTH_CODE = AUTH_CODE;
        this.userType = userType;
    }
}
