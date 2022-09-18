package com.dbsh.skup.user;

public class LectureInfo {
    String lectureName;     // 강의명
    String lectureCd;           // 학수번호
    String lectureNumber;       // 분반
    String lectureTime;         // 강의시간
    String professor;           // 담당교수 번호
    String year;                // 년도
    String term;                // 학기

    public LectureInfo() {
    }


    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getLectureCd() {
        return lectureCd;
    }

    public void setLectureCd(String lectureCd) {
        this.lectureCd = lectureCd;
    }

    public String getLectureNumber() {
        return lectureNumber;
    }

    public void setLectureNumber(String lectureNumber) {
        this.lectureNumber = lectureNumber;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(String lectureTime) {
        this.lectureTime = lectureTime;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
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
}
