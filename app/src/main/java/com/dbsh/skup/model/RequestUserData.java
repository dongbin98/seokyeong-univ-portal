package com.dbsh.skup.model;

public class RequestUserData {
    /*
        payload.put("username", userId);
		payload.put("password", userPw);
		payload.put("grant_type", "password");
		payload.put("userType", "sku");
     */

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrantType() {
        return grant_type;
    }

    public void setGrantType(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "RequestUserData{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", grant_type='" + grant_type + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
