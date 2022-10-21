package com.dbsh.skup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLecturePlanSummaryMap {
	// 교과목 정보
	@SerializedName("DEPT_SHYR")    // 개설학과
	@Expose
	private String deptShyr;

	@SerializedName("SUBJ_CLSS")    // 학수번호-분반
	@Expose
	private String subjClss;

	@SerializedName("COMPLETE_NM")  // 이수구분
	@Expose
	private String completeNm;

	@SerializedName("SUBJ_PONT")    // 학점
	@Expose
	private String subjPont;

	@SerializedName("SUBJ_TIME")    // 주 수업시간
	@Expose
	private String subjTime;

	@SerializedName("DAY")          // 요일 (다수 요일 시 , 로 구분)
	@Expose
	private String day;

	@SerializedName("TIME")         // 강의 시간 (다수 요일 수업 시 , 로 구분)
	@Expose
	private String time;

	@SerializedName("ROOM")         // 강의실
	@Expose
	private String room;

	// 담당교수정보
	@SerializedName("NAME")         // 교수명
	@Expose
	private String name;

	@SerializedName("HP_NO")        // 전화번호
	@Expose
	private String hpNo;

	@SerializedName("E_MAIL")       // 이메일
	@Expose
	private String eMail;

	// 교과목 개요
	@SerializedName("LECT_PURP")
	@Expose
	private String lectPurp;

	// 강의목표
	@SerializedName("LTPG_MTHD")
	@Expose
	private String ltpgMthd;

	// 강의방법
	@SerializedName("LECTURE_YN")       // 강의 유무
	@Expose
	private String lectureYn;

	@SerializedName("INVITATION_YN")    // 초청강의 유무
	@Expose
	private String invitationYn;

	@SerializedName("PROBLEM_YN")       // 문제중심 유무
	@Expose
	private String problemYn;

	@SerializedName("ONLINE_YN")        // 온라인 수업 유무
	@Expose
	private String onlineYn;

	@SerializedName("TEAM_YN")          // 소그룹(탐) 협동학습 유무
	@Expose
	private String teamYn;

	@SerializedName("DEBATE_YN")        // 토론/토의 유무
	@Expose
	private String debateYn;

	@SerializedName("EXPERIENCE_YN")    // 현장답사/체험 유무
	@Expose
	private String experienceYn;

	@SerializedName("INDIVIDUAL_YN")    // 개별학습 유무
	@Expose
	private String individualYn;

	@SerializedName("ACTION_YN")        // 액션러닝 유무
	@Expose
	private String actionYn;

	@SerializedName("PROJECT_YN")       // 프로젝트 유무
	@Expose
	private String projectYn;

	@SerializedName("PRACTICAL_YN")     // 실험/실습/실기 유무
	@Expose
	private String practicalYn;

	@SerializedName("FLIP_YN")          // 플립러닝 유무
	@Expose
	private String flipYn;

	// 평가방법
	@SerializedName("MID_EXAM_RATE")     // 중간고사
	@Expose
	private String midExamRate;

	@SerializedName("FINAL_EXAM_RATE")  // 기말고사
	@Expose
	private String finalExamRate;

	@SerializedName("REPORT_RATE")      // 과제점수
	@Expose
	private String reportRate;

	@SerializedName("ATTEND_RATE")      // 출석점수
	@Expose
	private String attendRate;

	@SerializedName("ETC1_RATE")        // 기타점수
	@Expose
	private String etcRate;
	// 전공능력
	@SerializedName("MAJOR_MTHD")
	@Expose
	private String majorMthd;
	// 강의규정 또는 안내사항
	@SerializedName("REMK_TEXT")
	@Expose
	private String remkText;

	public String getDeptShyr() {
		return deptShyr;
	}

	public String getSubjClss() {
		return subjClss;
	}

	public String getCompleteNm() {
		return completeNm;
	}

	public String getSubjPont() {
		return subjPont;
	}

	public String getSubjTime() {
		return subjTime;
	}

	public String getDay() {
		return day;
	}

	public String getTime() {
		return time;
	}

	public String getRoom() {
		return room;
	}

	public String getName() {
		return name;
	}

	public String getHpNo() {
		return hpNo;
	}

	public String geteMail() {
		return eMail;
	}

	public String getLectPurp() {
		return lectPurp;
	}

	public String getLtpgMthd() {
		return ltpgMthd;
	}

	public String getLectureYn() {
		return lectureYn;
	}

	public String getInvitationYn() {
		return invitationYn;
	}

	public String getProblemYn() {
		return problemYn;
	}

	public String getOnlineYn() {
		return onlineYn;
	}

	public String getTeamYn() {
		return teamYn;
	}

	public String getDebateYn() {
		return debateYn;
	}

	public String getExperienceYn() {
		return experienceYn;
	}

	public String getIndividualYn() {
		return individualYn;
	}

	public String getActionYn() {
		return actionYn;
	}

	public String getProjectYn() {
		return projectYn;
	}

	public String getPracticalYn() {
		return practicalYn;
	}

	public String getFlipYn() {
		return flipYn;
	}

	public String getMidExamRate() {
		return midExamRate;
	}

	public String getFinalExamRate() {
		return finalExamRate;
	}

	public String getReportRate() {
		return reportRate;
	}

	public String getAttendRate() {
		return attendRate;
	}

	public String getEtcRate() {
		return etcRate;
	}

	public String getMajorMthd() {
		return majorMthd;
	}

	public String getRemkText() {
		return remkText;
	}
}
