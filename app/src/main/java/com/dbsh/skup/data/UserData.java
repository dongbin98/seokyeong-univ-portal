package com.dbsh.skup.data;

import android.app.Application;

import com.dbsh.skup.model.ResponseYearList;

import java.util.ArrayList;

public class UserData extends Application {
    private String id;              // 학번
    private String korName;         // 이름
    private String phoneNumber;     // 전화번호
    private String colName;         // 단대
    private String deptName;        // 학과
    private String emailAddress;    // 이메일주소
    private String webmailAddress;  // 학교메일주소
    private String tutorName;       // 멘토교수
    private String schYear;         // 현재 년도
    private String schTerm;         // 현재 학기
    private String schYR;           // 현재 학년
    private String schReg;          // 재학 휴학 여부(한글)
    private String token;           // 로그인 토큰
	private ArrayList<ResponseYearList> yearList;        // 입학 ~ 지금까지 년도

	/* Setter */
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setKorName(String korName) {
		this.korName = korName;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setMajor(String colName, String deptName) {
		this.colName = colName;
		this.deptName = deptName;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public void setWebmailAddress(String webmailAddress) {
		this.webmailAddress = webmailAddress;
	}
	
	public void setTutorName(String tutorName) {
		this.tutorName = tutorName;
	}

	public void setSchInfo(String schYear, String schTerm, String schYR, String schReg) {
		this.schReg = schReg;
		this.schTerm = schTerm;
		this.schYR = schYR;
		this.schYear = schYear;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	/* Getter */
	public String getId() {
		return id;
	}
	
	public String getKorName() {
		return korName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getColName() {
		return colName;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public String getWebmailAddress() {
		return webmailAddress;
	}
	
	public String getTutorName() {
		return tutorName;
	}
	
	public String getSchYear() {
		return schYear;
	}
	
	public String getSchTerm() {
		return schTerm;
	}
	
	public String getSchYR() {
		return schYR;
	}
	
	public String getSchReg() {
		return schReg;
	}
	
	public String getToken() {
		return token;
	}

	public ArrayList<ResponseYearList> getYearList() {
		return yearList;
	}

	public void setYearList(ArrayList<ResponseYearList> yearList) {
		this.yearList = yearList;
	}
}
