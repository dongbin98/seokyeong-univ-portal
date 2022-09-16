package com.dbsh.skup.user;

import android.app.Application;

import java.util.ArrayList;

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
    private ArrayList<String> lectureList;             // 해당학기 수강과목 학수번호 리스트
    private ArrayList<String> lectureNumberList;       // 해당학기 수강과목 학수번호 리스트
    private ArrayList<String> lectureTimeList;         // 해당학기 수강과목 수업시간 리스트
    private ArrayList<String> lectureProfessorList;    // 해당학기 수강과목 담당교수 리스트
    private ArrayList<String> yearList;             // 입학이후 년도 리스트
    
    public User() {
	    lectureList = new ArrayList<>();
	    lectureNumberList = new ArrayList<>();
	    lectureTimeList = new ArrayList<>();
	    lectureProfessorList = new ArrayList<>();
        yearList = new ArrayList<>();
    }
	
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

	public void addLectureList(String lect) {this.lectureList.add(lect); }
	public void addLectureNumberList(String lect_num) {this.lectureNumberList.add(lect_num); }
	public void addLectureTimeList(String lect_time) {this.lectureTimeList.add(lect_time); }
	public void addLectureProfessorList(String prof_num) {this.lectureProfessorList.add(prof_num); }
	public void addYearList(String year) { this.yearList.add(year); }
	
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
	
	public ArrayList<String> getLectureList() {
		return lectureList;
	}
	
	public ArrayList<String> getLectureNumberList() {
		return lectureNumberList;
	}
	
	public ArrayList<String> getLectureTimeList() {
		return lectureTimeList;
	}
	
	public ArrayList<String> getLectureProfessorList() {
		return lectureProfessorList;
	}
	
	public ArrayList<String> getYearList() {
		return yearList;
	}

	public void clearLectureList() { this.lectureList.clear(); }

	public void clearLectureNumberList() { this.lectureNumberList.clear(); }

	public void clearLectureTimeList() { this.lectureTimeList.clear(); }

	public void clearLectureProfessorList() { this.lectureProfessorList.clear(); }

	public void clearYearList() {
		this.yearList.clear();
	}
}
