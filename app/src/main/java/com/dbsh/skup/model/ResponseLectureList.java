package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLectureList {
	@SerializedName("CLASSDAY")     // 수업요일
	@Expose
	private String lectureDay;

	@SerializedName("CLSS_NUMB")    // 분반
	@Expose
	private String lectureNumber;

	@SerializedName("SUBJ_CD")      // 학수번호
	@Expose
	private String lectureCd;

	@SerializedName("SUBJ_TIME")    // 강의시간
	@Expose
	private String lectureTime;

	@SerializedName("CLASS_NAME")   // 수업명
	@Expose
	private String lectureName;

	@SerializedName("PROF_NO")      // 교수아이디
	@Expose
	private String profNo;

	@SerializedName("PROF_NAME")    // 교수명
	@Expose
	private String profName;

	@SerializedName("CLASSHOUR_START_TIME") // 수업시작시간
	@Expose
	private String lectureStartTime;

	@SerializedName("CLASSHOUR_END_TIME")   // 수업종료시간
	@Expose
	private String lectureEndTime;

	@SerializedName("LECT_YEAR")    // 해당 년도
	@Expose
	private String year;

	@SerializedName("LECT_TERM")    // 해당 학기
	@Expose
	private String term;

	@SerializedName("CLASSROOM_NAME")       // 강의실명
	@Expose
	private String classroomName;

	public String getLectureDay() {
		return lectureDay;
	}

	public void setLectureDay(String lectureDay) {
		this.lectureDay = lectureDay;
	}

	public String getLectureNumber() {
		return lectureNumber;
	}

	public void setLectureNumber(String lectureNumber) {
		this.lectureNumber = lectureNumber;
	}

	public String getLectureCd() {
		return lectureCd;
	}

	public void setLectureCd(String lectureCd) {
		this.lectureCd = lectureCd;
	}

	public String getLectureTime() {
		return lectureTime;
	}

	public void setLectureTime(String lectureTime) {
		this.lectureTime = lectureTime;
	}

	public String getLectureName() {
		return lectureName;
	}

	public void setLectureName(String lectureName) {
		this.lectureName = lectureName;
	}

	public String getLectureStartTime() {
		return lectureStartTime;
	}

	public void setLectureStartTime(String lectureStartTime) {
		this.lectureStartTime = lectureStartTime;
	}

	public String getLectureEndTime() {
		return lectureEndTime;
	}

	public void setLectureEndTime(String lectureEndTime) {
		this.lectureEndTime = lectureEndTime;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getProfNo() {
		return profNo;
	}

	public String getProfName() {
		return profName;
	}

	public String getClassroomName() {
		return classroomName;
	}
}
