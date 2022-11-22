package com.dbsh.skup.model;

public class RequestUserData {
    private String username;
    private String password;
    private String grant_type;
    private String userType;

    public RequestUserData(String username, String password, String grant_type, String userType) {
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
        this.userType = userType;
    }
}
