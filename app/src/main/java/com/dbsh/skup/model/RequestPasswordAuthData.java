package com.dbsh.skup.model;

public class RequestPasswordAuthData {
    /**
     * {
     *   "ID": "2017305045",
     *   "KOR_NAME": "염동빈",
     *   "RESI_NO": "981215",
     *   "SEARCH_TYPE": "PHONE_MOBILE",
     *   "userType": "sku"
     * }
     */
    private String ID;
    private String KOR_NAME;
    private String RESI_NO;
    private String SEARCH_TYPE;
    private String userType;

    public RequestPasswordAuthData(String ID, String KOR_NAME, String RESI_NO, String SEARCH_TYPE, String userType) {
        this.ID = ID;
        this.KOR_NAME = KOR_NAME;
        this.RESI_NO = RESI_NO;
        this.SEARCH_TYPE = SEARCH_TYPE;
        this.userType = userType;
    }
}
