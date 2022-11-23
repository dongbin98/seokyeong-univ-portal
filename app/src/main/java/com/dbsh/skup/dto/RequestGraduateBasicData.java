package com.dbsh.skup.dto;

public class RequestGraduateBasicData {
    /**
     * {
     *   "path": "common/selectOne",
     *   "SYS_ID": "AL",
     *   "MAP_ID": "education.ugd.UGD_03002_T.SELECT",
     *   "parameter": {
     *     "ID": "2017305045",
     *     "STU_NO": "2017305045"
     *   },
     *   "userID": "2017305045",
     *   "programID": "UGD_03002_T",
     *   "accessToken": "dc9942bd-0ce1-4348-adb4-386167641ee8"
     * }
     */
    private String MAP_ID;
    private String SYS_ID;
    private String accessToken;
    private String path;
    private String programID;
    private String userID;
    private RequestGraduateBasicParameterData parameter;

    public RequestGraduateBasicData(String MAP_ID, String SYS_ID, String accessToken, String path, String programID, String userID, RequestGraduateBasicParameterData parameter) {
        this.MAP_ID = MAP_ID;
        this.SYS_ID = SYS_ID;
        this.accessToken = accessToken;
        this.path = path;
        this.programID = programID;
        this.userID = userID;
        this.parameter = parameter;
    }
}
