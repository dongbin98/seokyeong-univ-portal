package com.dbsh.skup.dto;

public class RequestGraduateSubjectParameterData {
    /**
     *   "parameter": {
     *     "SCH_YEAR": "2022",
     *     "SCH_TERM": "1",
     *     "ID": "2017305045",
     *     "STU_NO": "2017305045"
     *   }
     */
    private String STU_NO;

    public RequestGraduateSubjectParameterData(String STU_NO) {
        this.STU_NO = STU_NO;
    }
}
