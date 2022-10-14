package com.dbsh.skup.model;

public class RequestGraduateSubjectData {
    /**
     * {
     *      "path": "common/selectList",
     *      "SYS_ID": "AL",
     *      "MAP_ID": "education.ugd.UGD_03031_T.SELECT_UCS_AREASUBJECT",
     *      "parameter": {
     *      "STU_NO": "2017305045"
     *      },
     *      "userID": "2017305045",
     *      "programID": "UGD_03031_T",
     *      "accessToken": "dc9942bd-0ce1-4348-adb4-386167641ee8"
     * }
     */
    private String MAP_ID;
    private String SYS_ID;
    private String accessToken;
    private String path;
    private String programID;
    private String userID;
    private RequestGraduateSubjectParameterData parameter;

    public RequestGraduateSubjectData(String MAP_ID, String SYS_ID, String accessToken, String path, String programID, String userID, RequestGraduateSubjectParameterData parameter) {
        this.MAP_ID = MAP_ID;
        this.SYS_ID = SYS_ID;
        this.accessToken = accessToken;
        this.path = path;
        this.programID = programID;
        this.userID = userID;
        this.parameter = parameter;
    }
}
