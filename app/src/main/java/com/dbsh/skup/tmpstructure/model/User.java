package com.dbsh.skup.tmpstructure.model;

import android.app.Application;

public class User extends Application {
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

	/* Constructor */
	public User() {

	}

	public User(String id, String korName, String phoneNumber, String colName, String deptName, String emailAddress, String webmailAddress, String tutorName, String schYear, String schTerm, String schYR, String schReg, String token) {
		this.id = id;
		this.korName = korName;
		this.phoneNumber = phoneNumber;
		this.colName = colName;
		this.deptName = deptName;
		this.emailAddress = emailAddress;
		this.webmailAddress = webmailAddress;
		this.tutorName = tutorName;
		this.schYear = schYear;
		this.schTerm = schTerm;
		this.schYR = schYR;
		this.schReg = schReg;
		this.token = token;
	}

	/* Getter Setter */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKorName() {
		return korName;
	}

	public void setKorName(String korName) {
		this.korName = korName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getWebmailAddress() {
		return webmailAddress;
	}

	public void setWebmailAddress(String webmailAddress) {
		this.webmailAddress = webmailAddress;
	}

	public String getTutorName() {
		return tutorName;
	}

	public void setTutorName(String tutorName) {
		this.tutorName = tutorName;
	}

	public String getSchYear() {
		return schYear;
	}

	public void setSchYear(String schYear) {
		this.schYear = schYear;
	}

	public String getSchTerm() {
		return schTerm;
	}

	public void setSchTerm(String schTerm) {
		this.schTerm = schTerm;
	}

	public String getSchYR() {
		return schYR;
	}

	public void setSchYR(String schYR) {
		this.schYR = schYR;
	}

	public String getSchReg() {
		return schReg;
	}

	public void setSchReg(String schReg) {
		this.schReg = schReg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
