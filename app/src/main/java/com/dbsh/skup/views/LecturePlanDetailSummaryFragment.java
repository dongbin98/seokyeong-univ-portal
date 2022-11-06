package com.dbsh.skup.views;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.LecturePlanDetailSummaryAdapter;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.LecturePlanDetailSummaryFormBinding;
import com.dbsh.skup.model.ResponseLecturePlanBookList;
import com.dbsh.skup.model.ResponseLecturePlanSummaryMap;
import com.dbsh.skup.mpandroidchart.YValueFormatter;
import com.dbsh.skup.viewmodels.LecturePlanDetailSummaryViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class LecturePlanDetailSummaryFragment extends Fragment {
	private LecturePlanDetailSummaryFormBinding binding;
	private LecturePlanDetailSummaryViewModel viewModel;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;

	private ArrayList<TextView> lectureInfo;
	private String subjectCd, classNumber, lectureYear, lectureTerm, professorId, lectureName;
	String token, id;

	List<LecturePlanDetailSummaryAdapter.BookItem> data;
	public LecturePlanDetailSummaryAdapter adapter;
	RecyclerView bookList;

	final int[] colors = {R.color.timetableitem3, R.color.mainBlue, R.color.timetableitem4, R.color.timetableitem5, R.color.timetableitem6,};

	UserData userData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.inflate(inflater, R.layout.lecture_plan_detail_summary_form, container, false);
		viewModel = new LecturePlanDetailSummaryViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();    // 바인딩 강제 즉시실행

		homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());
		lectureInfo = new ArrayList<>();
		data = new ArrayList<>();

		userData = ((UserData) getActivity().getApplication());

		token = userData.getToken();
		id = userData.getId();

		if (getArguments() != null) {
			System.out.println("데이터가 있습니다!");
			subjectCd = getArguments().getString("SUBJ_CD");
			classNumber = getArguments().getString("CLSS_NUMB");
			lectureYear = getArguments().getString("LECT_YEAR");
			lectureTerm = getArguments().getString("LECT_TERM");
			professorId = getArguments().getString("STAF_NO");
			lectureName = getArguments().getString("LECT_NAME");
		}

		bookList = binding.lectureplanDetailSummaryRecyclerview;
		adapter = new LecturePlanDetailSummaryAdapter(data);

		GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
		bookList.setLayoutManager(gridLayoutManager);
		bookList.setAdapter(adapter);
		bookList.addItemDecoration(new SpaceItemDecoration(int2dp(16)));

		viewModel.getLecturePlanSummary(token, id, subjectCd, classNumber, lectureYear, lectureTerm, professorId);
		viewModel.getLecturePlanBook(token, id, subjectCd, classNumber, lectureYear, lectureTerm, professorId);

		viewModel.responseLecturePlanSummaryMapLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseLecturePlanSummaryMap>() {
			@SuppressLint({"ResourceType", "SetTextI18n"})
			@Override
			public void onChanged(ResponseLecturePlanSummaryMap responseLecturePlanSummaryMap) {
				int viewWidth = (binding.lectureplanDetailSummaryLayout.getWidth());
				System.out.println("width = " + viewWidth);
				int topToBottom = binding.lectureplanDetailSummaryTitle1.getId();
				int textLength  = 0;
				int marginTop = 16;
				// 교과목정보
				// -> 학부
				if(responseLecturePlanSummaryMap.getDeptShyr() != null) {
					TextView tv = new TextView(getContext());
					tv.setId(View.generateViewId());
					tv.setText(responseLecturePlanSummaryMap.getDeptShyr());
					tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
					tv.setTextSize(12);
					tv.setPadding(int2dp(12), int2dp(6), int2dp(12), int2dp(6));
					tv.setTextColor(getContext().getColor(R.color.mainBlue));
					tv.setBackground(getContext().getDrawable(R.drawable.textview_subsky_background));
					// += (textsize + padding/2)
					textLength += int2dp(tv.getText().length() * 12) + int2dp(12);

					System.out.println(textLength);
					// Text Style
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
						tv.setTypeface(getResources().getFont(R.font.roboto), Typeface.NORMAL);
					} else {
						tv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto.ttf"), Typeface.NORMAL);
					}
					ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, int2dp(28));
					lp.topToBottom = topToBottom;
					lp.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
					lp.setMargins(0, int2dp(marginTop), 0, 0);

					tv.setLayoutParams(lp);
					lectureInfo.add(tv);
					binding.lectureplanDetailSummaryLayout.addView(tv);
					// += (margin)
					textLength += int2dp(12);
				}
				// -> 이수구분
				if(responseLecturePlanSummaryMap.getCompleteNm() != null) {
					TextView tv = new TextView(getContext());
					tv.setId(View.generateViewId());
					tv.setText(responseLecturePlanSummaryMap.getCompleteNm());
					tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
					tv.setTextSize(12);
					tv.setPadding(int2dp(12), int2dp(6), int2dp(12), int2dp(6));
					tv.setTextColor(getContext().getColor(R.color.mainBlue));
					tv.setBackground(getContext().getDrawable(R.drawable.textview_subsky_background));
					// += (textsize + padding/2)
					textLength += int2dp(tv.getText().length() * 12) + int2dp(12);
					System.out.println(textLength);
					// Text Style
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
						tv.setTypeface(getResources().getFont(R.font.roboto), Typeface.NORMAL);
					} else {
						tv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto.ttf"), Typeface.NORMAL);
					}
					ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, int2dp(28));
					if(viewWidth < textLength) {
						// 줄넘김
						textLength = 0;
						marginTop = 8;
						topToBottom = lectureInfo.get(lectureInfo.size()-1).getId();
						lp.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
						lp.setMargins(0, int2dp(marginTop), 0, 0);
					} else {
						lp.leftToRight = lectureInfo.get(lectureInfo.size() - 1).getId();
						lp.setMargins(int2dp(12), int2dp(marginTop), 0, 0);
					}
					lp.topToBottom = topToBottom;

					tv.setLayoutParams(lp);
					lectureInfo.add(tv);
					binding.lectureplanDetailSummaryLayout.addView(tv);
					// += (margin)
					textLength += int2dp(12);
				}
				// -> 학수번호
				if(responseLecturePlanSummaryMap.getSubjClss() != null) {
					TextView tv = new TextView(getContext());
					tv.setId(View.generateViewId());
					tv.setText(responseLecturePlanSummaryMap.getSubjClss());
					tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
					tv.setTextSize(12);
					tv.setPadding(int2dp(12), int2dp(6), int2dp(12), int2dp(6));
					tv.setTextColor(getContext().getColor(R.color.mainBlue));
					tv.setBackground(getContext().getDrawable(R.drawable.textview_subsky_background));
					// += (textsize + padding/2)
					textLength += int2dp(tv.getText().length() * 12) + int2dp(12);
					System.out.println(textLength);
					// Text Style
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
						tv.setTypeface(getResources().getFont(R.font.roboto), Typeface.NORMAL);
					} else {
						tv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto.ttf"), Typeface.NORMAL);
					}
					ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, int2dp(28));
					if(viewWidth < textLength) {
						// 줄넘김
						textLength = 0;
						marginTop = 8;
						topToBottom = lectureInfo.get(lectureInfo.size()-1).getId();
						lp.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
						lp.setMargins(0, int2dp(marginTop), 0, 0);
					} else {
						lp.leftToRight = lectureInfo.get(lectureInfo.size() - 1).getId();
						lp.setMargins(int2dp(12), int2dp(marginTop), 0, 0);
					}
					lp.topToBottom = topToBottom;

					tv.setLayoutParams(lp);
					lectureInfo.add(tv);
					binding.lectureplanDetailSummaryLayout.addView(tv);
					// += (margin)
					textLength += int2dp(12);
				}
				// -> 학점/시간
				if(responseLecturePlanSummaryMap.getSubjPont() != null && responseLecturePlanSummaryMap.getSubjTime() != null) {
					TextView tv = new TextView(getContext());
					tv.setId(View.generateViewId());
					tv.setText(responseLecturePlanSummaryMap.getSubjPont() + "학점/" + responseLecturePlanSummaryMap.getSubjTime() + "시간");
					tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
					tv.setTextSize(12);
					tv.setPadding(int2dp(12), int2dp(6), int2dp(12), int2dp(6));
					tv.setTextColor(getContext().getColor(R.color.mainBlue));
					tv.setBackground(getContext().getDrawable(R.drawable.textview_subsky_background));
					// += (textsize + padding/2)
					textLength += int2dp(tv.getText().length() * 12) + int2dp(12);
					System.out.println(textLength);
					// Text Style
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
						tv.setTypeface(getResources().getFont(R.font.roboto), Typeface.NORMAL);
					} else {
						tv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto.ttf"), Typeface.NORMAL);
					}
					ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, int2dp(28));
					if(viewWidth < textLength) {
						// 줄넘김
						textLength = 0;
						marginTop = 8;
						topToBottom = lectureInfo.get(lectureInfo.size()-1).getId();
						lp.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
						lp.setMargins(0, int2dp(marginTop), 0, 0);
					} else {
						lp.leftToRight = lectureInfo.get(lectureInfo.size() - 1).getId();
						lp.setMargins(int2dp(12), int2dp(marginTop), 0, 0);
					}
					lp.topToBottom = topToBottom;

					tv.setLayoutParams(lp);
					lectureInfo.add(tv);
					binding.lectureplanDetailSummaryLayout.addView(tv);
					// += (margin)
					textLength += int2dp(12);
				}

				// -> 강의시간 (복수처리)
				if(responseLecturePlanSummaryMap.getTime() != null && responseLecturePlanSummaryMap.getDay() != null) {
					String[] times = responseLecturePlanSummaryMap.getTime().split(",");
					String[] days = responseLecturePlanSummaryMap.getDay().split(",");
					for(int i = 0; i < times.length; i++) {
						TextView tv = new TextView(getContext());
						tv.setId(View.generateViewId());
						tv.setText(days[i] + "-" + times[i]);
						tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
						tv.setTextSize(12);
						tv.setPadding(int2dp(12), int2dp(6), int2dp(12), int2dp(6));
						tv.setTextColor(getContext().getColor(R.color.mainBlue));
						tv.setBackground(getContext().getDrawable(R.drawable.textview_subsky_background));
						// += (textsize + padding/2)
						textLength += int2dp(tv.getText().length() * 12) + int2dp(12);
						System.out.println(textLength);
						// Text Style
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
							tv.setTypeface(getResources().getFont(R.font.roboto), Typeface.NORMAL);
						} else {
							tv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto.ttf"), Typeface.NORMAL);
						}
						ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, int2dp(28));
						if(viewWidth < textLength) {
							// 줄넘김
							textLength = 0;
							marginTop = 8;
							topToBottom = lectureInfo.get(lectureInfo.size()-1).getId();
							lp.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
							lp.setMargins(0, int2dp(marginTop), 0, 0);
						} else {
							lp.leftToRight = lectureInfo.get(lectureInfo.size() - 1).getId();
							lp.setMargins(int2dp(12), int2dp(marginTop), 0, 0);
						}
						lp.topToBottom = topToBottom;

						tv.setLayoutParams(lp);
						lectureInfo.add(tv);
						binding.lectureplanDetailSummaryLayout.addView(tv);
						// += (margin)
						textLength += int2dp(12);
					}
				}
				// -> 강의실
				if(responseLecturePlanSummaryMap.getRoom() != null) {
					TextView tv = new TextView(getContext());
					tv.setId(View.generateViewId());
					tv.setText(responseLecturePlanSummaryMap.getRoom());
					tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
					tv.setTextSize(12);
					tv.setPadding(int2dp(12), int2dp(6), int2dp(12), int2dp(6));
					tv.setTextColor(getContext().getColor(R.color.mainBlue));
					tv.setBackground(getContext().getDrawable(R.drawable.textview_subsky_background));
					// += (textsize + padding/2)
					textLength += int2dp(tv.getText().length() * 12) + int2dp(12);
					System.out.println(textLength);
					// Text Style
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
						tv.setTypeface(getResources().getFont(R.font.roboto), Typeface.NORMAL);
					} else {
						tv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto.ttf"), Typeface.NORMAL);
					}
					ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, int2dp(28));
					if(viewWidth < textLength) {
						// 줄넘김
						textLength = 0;
						marginTop = 8;
						topToBottom = lectureInfo.get(lectureInfo.size()-1).getId();
						lp.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
						lp.setMargins(0, int2dp(marginTop), 0, 0);
					} else {
						lp.leftToRight = lectureInfo.get(lectureInfo.size() - 1).getId();
						lp.setMargins(int2dp(12), int2dp(marginTop), 0, 0);
					}
					lp.topToBottom = topToBottom;

					tv.setLayoutParams(lp);
					lectureInfo.add(tv);
					binding.lectureplanDetailSummaryLayout.addView(tv);
				}

				// 담당교수정보 타이틀바 위치조정
				ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				lp.setMargins(0, int2dp(32), 0, 0);
				lp.topToBottom = lectureInfo.get(lectureInfo.size()-1).getId();
				lp.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
				binding.lectureplanDetailSummaryTitle2.setLayoutParams(lp);

				// 교수
				binding.lectureplanDetailSummaryProfessorName.setText((responseLecturePlanSummaryMap.getName() != null ? responseLecturePlanSummaryMap.getName() : ""));
				binding.lectureplanDetailSummaryProfessorPhone.setText((responseLecturePlanSummaryMap.getHpNo() != null ? responseLecturePlanSummaryMap.getHpNo() : "없음"));
				binding.lectureplanDetailSummaryProfessorEmail.setText((responseLecturePlanSummaryMap.geteMail() != null ? responseLecturePlanSummaryMap.geteMail() : "없음"));
				// 교과목 개요
				binding.lectureplanDetailSummarySummary.setText((responseLecturePlanSummaryMap.getLectPurp() != null ? responseLecturePlanSummaryMap.getLectPurp() : "없음"));
				// 강의목표
				binding.lectureplanDetailSummaryGoal.setText((responseLecturePlanSummaryMap.getLtpgMthd() != null ? responseLecturePlanSummaryMap.getLtpgMthd() : "없음"));
				// 강의방법
				binding.lectureplanDetailSummaryIsLecture.setBackground((responseLecturePlanSummaryMap.getLectureYn().equals("Y") ? getContext().getDrawable(R.drawable.textview_subsky_background) : getContext().getDrawable(R.drawable.textview_disable_background)));
				binding.lectureplanDetailSummaryIsLecture.setTextColor((responseLecturePlanSummaryMap.getLectureYn().equals("Y") ? getContext().getColor(R.color.mainBlue) : getContext().getColor(R.color.gray3)));

				binding.lectureplanDetailSummaryIsDebate.setBackground((responseLecturePlanSummaryMap.getDebateYn().equals("Y") ? getContext().getDrawable(R.drawable.textview_subsky_background) : getContext().getDrawable(R.drawable.textview_disable_background)));
				binding.lectureplanDetailSummaryIsDebate.setTextColor((responseLecturePlanSummaryMap.getDebateYn().equals("Y") ? getContext().getColor(R.color.mainBlue) : getContext().getColor(R.color.gray3)));

				binding.lectureplanDetailSummaryIsPractical.setBackground((responseLecturePlanSummaryMap.getPracticalYn().equals("Y") ? getContext().getDrawable(R.drawable.textview_subsky_background) : getContext().getDrawable(R.drawable.textview_disable_background)));
				binding.lectureplanDetailSummaryIsPractical.setTextColor((responseLecturePlanSummaryMap.getPracticalYn().equals("Y") ? getContext().getColor(R.color.mainBlue) : getContext().getColor(R.color.gray3)));

				binding.lectureplanDetailSummaryIsTeam.setBackground((responseLecturePlanSummaryMap.getTeamYn().equals("Y") ? getContext().getDrawable(R.drawable.textview_subsky_background) : getContext().getDrawable(R.drawable.textview_disable_background)));
				binding.lectureplanDetailSummaryIsTeam.setTextColor((responseLecturePlanSummaryMap.getTeamYn().equals("Y") ? getContext().getColor(R.color.mainBlue) : getContext().getColor(R.color.gray3)));

				binding.lectureplanDetailSummaryIsProblem.setBackground((responseLecturePlanSummaryMap.getProblemYn().equals("Y") ? getContext().getDrawable(R.drawable.textview_subsky_background) : getContext().getDrawable(R.drawable.textview_disable_background)));
				binding.lectureplanDetailSummaryIsProblem.setTextColor((responseLecturePlanSummaryMap.getProblemYn().equals("Y") ? getContext().getColor(R.color.mainBlue) : getContext().getColor(R.color.gray3)));

				binding.lectureplanDetailSummaryIsProject.setBackground((responseLecturePlanSummaryMap.getProjectYn().equals("Y") ? getContext().getDrawable(R.drawable.textview_subsky_background) : getContext().getDrawable(R.drawable.textview_disable_background)));
				binding.lectureplanDetailSummaryIsProject.setTextColor((responseLecturePlanSummaryMap.getProjectYn().equals("Y") ? getContext().getColor(R.color.mainBlue) : getContext().getColor(R.color.gray3)));

				binding.lectureplanDetailSummaryIsFlip.setBackground((responseLecturePlanSummaryMap.getFlipYn().equals("Y") ? getContext().getDrawable(R.drawable.textview_subsky_background) : getContext().getDrawable(R.drawable.textview_disable_background)));
				binding.lectureplanDetailSummaryIsFlip.setTextColor((responseLecturePlanSummaryMap.getFlipYn().equals("Y") ? getContext().getColor(R.color.mainBlue) : getContext().getColor(R.color.gray3)));

				binding.lectureplanDetailSummaryIsInvitation.setBackground((responseLecturePlanSummaryMap.getInvitationYn().equals("Y") ? getContext().getDrawable(R.drawable.textview_subsky_background) : getContext().getDrawable(R.drawable.textview_disable_background)));
				binding.lectureplanDetailSummaryIsInvitation.setTextColor((responseLecturePlanSummaryMap.getInvitationYn().equals("Y") ? getContext().getColor(R.color.mainBlue) : getContext().getColor(R.color.gray3)));

				binding.lectureplanDetailSummaryIsIndividual.setBackground((responseLecturePlanSummaryMap.getIndividualYn().equals("Y") ? getContext().getDrawable(R.drawable.textview_subsky_background) : getContext().getDrawable(R.drawable.textview_disable_background)));
				binding.lectureplanDetailSummaryIsIndividual.setTextColor((responseLecturePlanSummaryMap.getIndividualYn().equals("Y") ? getContext().getColor(R.color.mainBlue) : getContext().getColor(R.color.gray3)));

				binding.lectureplanDetailSummaryIsExperience.setBackground((responseLecturePlanSummaryMap.getExperienceYn().equals("Y") ? getContext().getDrawable(R.drawable.textview_subsky_background) : getContext().getDrawable(R.drawable.textview_disable_background)));
				binding.lectureplanDetailSummaryIsExperience.setTextColor((responseLecturePlanSummaryMap.getExperienceYn().equals("Y") ? getContext().getColor(R.color.mainBlue) : getContext().getColor(R.color.gray3)));

				binding.lectureplanDetailSummaryIsOnline.setBackground((responseLecturePlanSummaryMap.getOnlineYn().equals("Y") ? getContext().getDrawable(R.drawable.textview_subsky_background) : getContext().getDrawable(R.drawable.textview_disable_background)));
				binding.lectureplanDetailSummaryIsOnline.setTextColor((responseLecturePlanSummaryMap.getOnlineYn().equals("Y") ? getContext().getColor(R.color.mainBlue) : getContext().getColor(R.color.gray3)));

				binding.lectureplanDetailSummaryIsAction.setBackground((responseLecturePlanSummaryMap.getActionYn().equals("Y") ? getContext().getDrawable(R.drawable.textview_subsky_background) : getContext().getDrawable(R.drawable.textview_disable_background)));
				binding.lectureplanDetailSummaryIsAction.setTextColor((responseLecturePlanSummaryMap.getActionYn().equals("Y") ? getContext().getColor(R.color.mainBlue) : getContext().getColor(R.color.gray3)));
				// 평가방법
				binding.lectureplanDetailSummaryChart.setDrawHoleEnabled(false);
				binding.lectureplanDetailSummaryChart.setUsePercentValues(true);
				binding.lectureplanDetailSummaryChart.setEntryLabelTextSize(14);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					binding.lectureplanDetailSummaryChart.setEntryLabelTypeface(getResources().getFont(R.font.roboto));
				} else {
					binding.lectureplanDetailSummaryChart.setEntryLabelTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto.ttf"));
				}
				binding.lectureplanDetailSummaryChart.setEntryLabelColor(getContext().getColor(R.color.onlyWhite));	// 이거 적용 안됨
				binding.lectureplanDetailSummaryChart.getDescription().setEnabled(false);
				binding.lectureplanDetailSummaryChart.setExtraOffsets(5, 10, 5, 5);
				binding.lectureplanDetailSummaryChart.setDragDecelerationFrictionCoef(0.95f);
				binding.lectureplanDetailSummaryChart.setHoleColor(R.color.white);
				binding.lectureplanDetailSummaryChart.setTransparentCircleRadius(61f);

				Legend legend = binding.lectureplanDetailSummaryChart.getLegend();
				legend.setEnabled(false);

				ArrayList<PieEntry> yValues = new ArrayList<>();
				if(!responseLecturePlanSummaryMap.getMidExamRate().equals("0"))
					yValues.add(new PieEntry(Float.parseFloat(responseLecturePlanSummaryMap.getMidExamRate()), "중간고사"));
				if(!responseLecturePlanSummaryMap.getFinalExamRate().equals("0"))
					yValues.add(new PieEntry(Float.parseFloat(responseLecturePlanSummaryMap.getFinalExamRate()), "기말고사"));
				if(!responseLecturePlanSummaryMap.getReportRate().equals("0"))
					yValues.add(new PieEntry(Float.parseFloat(responseLecturePlanSummaryMap.getReportRate()), "과제"));
				if(!responseLecturePlanSummaryMap.getAttendRate().equals("0"))
					yValues.add(new PieEntry(Float.parseFloat(responseLecturePlanSummaryMap.getAttendRate()), "출결"));
				if(!responseLecturePlanSummaryMap.getEtcRate().equals("0"))
					yValues.add(new PieEntry(Float.parseFloat(responseLecturePlanSummaryMap.getEtcRate()), "기타"));

				PieDataSet dataSet = new PieDataSet(yValues, "scores");
//				dataSet.setSliceSpace(1f);
				dataSet.setSelectionShift(5f);
				dataSet.setColors(colors, getContext());

				PieData data = new PieData(dataSet);
				dataSet.setDrawValues(true);
				data.setValueFormatter(new YValueFormatter());
				data.setValueTextSize(16);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					data.setValueTypeface(getResources().getFont(R.font.roboto));
				} else {
					data.setValueTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto.ttf"));
				}
				data.setValueTextColor(getContext().getColor(R.color.onlyWhite));	// 얘도 적용 안됨

//				((PieChartRenderer) binding.lectureplanDetailSummaryChart.getRenderer()).getPaintEntryLabels().setColor(Color.WHITE);
//				binding.lectureplanDetailSummaryChart.setEntryLabelColor(R.color.onlyWhite);
				binding.lectureplanDetailSummaryChart.setData(data);
				binding.lectureplanDetailSummaryChart.invalidate();
				binding.lectureplanDetailSummaryChart.animateY(1000, Easing.EaseInOutCubic);

				// 전공능력
				binding.lectureplanDetailSummaryAbility.setText((responseLecturePlanSummaryMap.getMajorMthd() != null ? responseLecturePlanSummaryMap.getMajorMthd() : "없음"));
				// 강의규정 또는 안내사항
				binding.lectureplanDetailSummaryNotice.setText((responseLecturePlanSummaryMap.getRemkText() != null ? responseLecturePlanSummaryMap.getRemkText() : "없음"));
			}
	});

		viewModel.responseLecturePlanBookListLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ResponseLecturePlanBookList>>() {
			@Override
			public void onChanged(ArrayList<ResponseLecturePlanBookList> responseLecturePlanBookLists) {
				for(ResponseLecturePlanBookList responseLecturePlanBookList : responseLecturePlanBookLists) {
					// 강의 참고 교재 처리
					data.add(new LecturePlanDetailSummaryAdapter.BookItem(
							responseLecturePlanBookList.getBookName(),
							responseLecturePlanBookList.getBookAuthor(),
							responseLecturePlanBookList.getBookPubYear(),
							responseLecturePlanBookList.getBookPubCo()
					));
					adapter.notifyItemInserted(data.size());
				}
			}
		});

		return binding.getRoot();
	}

	public int int2dp(int value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
	}

	public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
		private int spacing;

		public SpaceItemDecoration(int spacing) {
			this.spacing = spacing;
		}

		@Override
		public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
			super.getItemOffsets(outRect, view, parent, state);
			int index = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
			int position = parent.getChildLayoutPosition(view);
			if(index == 0) {
				outRect.right = spacing / 2;
			} else {
				outRect.left = spacing / 2;
			}
		}
	}
}
