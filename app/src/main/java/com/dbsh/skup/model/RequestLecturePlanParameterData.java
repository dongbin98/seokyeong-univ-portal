package com.dbsh.skup.model;

public class RequestLecturePlanParameterData {
    /**
     *   "parameter": {
     *     "S_LECT_YEAR": "2022",
     *     "S_LECT_TERM": "2",
     *     "S_UCS04": "%",
     *     "S_DEPT02": "%",
     *     "S_DEPT03": "%",
     *     "S_DEPT04": "%",
     *     "S_INPUT_YSNO": "1",
     *     "S_FROM_DT": ""
     *   },
     */
    private String S_LECT_YEAR;
    private String S_LECT_TERM;
    private String S_UCS04;
    private String S_DEPT02;
    private String S_DEPT03;
    private String S_DEPT04;
    private String S_INPUT_YSNO;
    private String S_FROM_DT;

    public RequestLecturePlanParameterData(String s_LECT_YEAR, String s_LECT_TERM, String s_UCS04, String s_DEPT02, String s_DEPT03, String s_DEPT04, String s_INPUT_YSNO, String s_FROM_DT) {
        S_LECT_YEAR = s_LECT_YEAR;
        S_LECT_TERM = s_LECT_TERM;
        S_UCS04 = s_UCS04;
        S_DEPT02 = s_DEPT02;
        S_DEPT03 = s_DEPT03;
        S_DEPT04 = s_DEPT04;
        S_INPUT_YSNO = s_INPUT_YSNO;
        S_FROM_DT = s_FROM_DT;
    }
}
