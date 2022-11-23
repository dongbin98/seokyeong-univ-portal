package com.dbsh.skup.dto;

public class RequestGraduateBasicParameterData {
    /**
     * {
     *   "parameter": {
     *     "ID": "2017305045",
     *     "STU_NO": "2017305045"
     *   }
     */
    private String ID;
    private String STU_NO;

    public RequestGraduateBasicParameterData(String ID, String STU_NO) {
        this.ID = ID;
        this.STU_NO = STU_NO;
    }
}
