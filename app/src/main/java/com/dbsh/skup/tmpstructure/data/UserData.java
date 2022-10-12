package com.dbsh.skup.tmpstructure.data;

import android.app.Application;

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
	private ArrayList<LectureData> lectureDatas;	// 강의정보리스트
    
    public UserData() {
		lectureDatas = new ArrayList<LectureData>();
    }

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

	/* clear ArrayList */
	public void clearLectureInfo() {
		this.lectureDatas.clear();
	}

	public ArrayList<LectureData> getLectureDatas() {
		return lectureDatas;
	}

	public void setLectureDatas(ArrayList<LectureData> lectureDatas) {
		this.lectureDatas = lectureDatas;
	}

	public void addLectureInfo(LectureData lectureData) {
		lectureDatas.add(lectureData);
	}

	@Override
	public String toString() {
		return "UserData{" +
				"id='" + id + '\'' +
				", korName='" + korName + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", colName='" + colName + '\'' +
				", deptName='" + deptName + '\'' +
				", emailAddress='" + emailAddress + '\'' +
				", webmailAddress='" + webmailAddress + '\'' +
				", tutorName='" + tutorName + '\'' +
				", schYear='" + schYear + '\'' +
				", schTerm='" + schTerm + '\'' +
				", schYR='" + schYR + '\'' +
				", schReg='" + schReg + '\'' +
				", token='" + token + '\'' +
				", lectureDatas=" + lectureDatas +
				'}';
	}
}
