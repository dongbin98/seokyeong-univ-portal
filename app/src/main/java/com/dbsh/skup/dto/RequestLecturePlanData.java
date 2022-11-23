package com.dbsh.skup.dto;

public class RequestLecturePlanData {
    /**
     * {
     *   "path": "common/selectList",
     *   "SYS_ID": "AL",
     *   "MAP_ID": "education.ucs.UCS_03100_T.SELECT",
     *   "userID": "2017305045",
     *   "programID": "UCS_03090_T",
     *   "accessToken": "9f7972cf-0153-4dfe-a0c9-2332a1c15bef"
     * }
     */
    private String MAP_ID;
    private String SYS_ID;
    private String accessToken;
    private String path;
    private String programID;
    private String userID;
    private RequestLecturePlanParameterData parameter;

    public RequestLecturePlanData(String MAP_ID, String SYS_ID, String accessToken, String path, String programID, String userID, RequestLecturePlanParameterData parameter) {
        this.MAP_ID = MAP_ID;
        this.SYS_ID = SYS_ID;
        this.accessToken = accessToken;
        this.path = path;
        this.programID = programID;
        this.userID = userID;
        this.parameter = parameter;
    }
}
