package com.dbsh.skup.views;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.dbsh.skup.R;
import com.dbsh.skup.model.UserData;
import com.dbsh.skup.databinding.GradeAllFormBinding;
import com.dbsh.skup.dto.ResponseGradeTermList;
import com.dbsh.skup.dto.ResponseGradeTotalCreditList;
import com.dbsh.skup.dto.ResponseGradeTotalMap;
import com.dbsh.skup.mpandroidchart.MyMarkerView;
import com.dbsh.skup.mpandroidchart.XAxisFormatter;
import com.dbsh.skup.mpandroidchart.XAxisRenderer;
import com.dbsh.skup.viewmodels.GradeAllViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class GradeAllFragment extends Fragment implements OnBackPressedListener {

    GradeAllFormBinding binding;
    GradeAllViewModel viewModel;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;

	// graph
	private LineChart gradeGraph;

	String token;
	String id;

	UserData userData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.grade_all_form, container, false);
        viewModel = new GradeAllViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

	    homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());
		gradeGraph = binding.gradeAllGraph;

	    userData = ((UserData) getActivity().getApplication());

	    token = userData.getToken();
	    id = userData.getId();

		viewModel.getGradeTotal(token, id);
		viewModel.getGradeTotalCredit(token, id);
		viewModel.getGradeTerm(token, id);

		viewModel.responseGradeTotalMapLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseGradeTotalMap>() {
			@Override
			public void onChanged(ResponseGradeTotalMap responseGradeTotalMap) {
				binding.gradeAllTotalCredit.setText(responseGradeTotalMap.getApplyPnt());
				binding.gradeAllPercentile.setText(responseGradeTotalMap.getTotScr());
				binding.gradeAllTotalAverage.setText(responseGradeTotalMap.getGrdMarkAvg());
			}
		});

		viewModel.responseGradeTotalCreditListLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ResponseGradeTotalCreditList>>() {
			@Override
			public void onChanged(ArrayList<ResponseGradeTotalCreditList> responseGradeTotalCreditLists) {
				// 0 -> 신청, 1 -> 취득
				// null 처리 필요 X -> 없는 값은 0으로 나옴
				// 전체 취득학점
				binding.gradeMajorTotalCredit.setText(responseGradeTotalCreditLists.get(0).getMajorTotalPoint());
				binding.gradeLiberalTotalCredit.setText(responseGradeTotalCreditLists.get(0).getLiberalTotalPoint());
				binding.gradeEtcTotalCredit.setText(responseGradeTotalCreditLists.get(0).getEtcTotalPoint());
				// 전공 취득 학점
				binding.gradeMajorCredit.setText(responseGradeTotalCreditLists.get(0).getMajorPoint());
				binding.gradeMajorDeepenCredit.setText(responseGradeTotalCreditLists.get(0).getMajorDeepenPoint());
				binding.gradeMajorCoreCredit.setText(responseGradeTotalCreditLists.get(0).getMajorCorePoint());
				// 교양 취득 학점
				binding.gradeLiberalRequirementCredit.setText(responseGradeTotalCreditLists.get(0).getLiberalRequirementPoint());
				binding.gradeLiberalSelectionCredit.setText(responseGradeTotalCreditLists.get(0).getLiberalSelectionPoint());
				// 기타 취득 학점
				binding.gradeDoubleMajorCredit.setText(responseGradeTotalCreditLists.get(0).getDoubleMajorPint());
				binding.gradeNormalSelectionCredit.setText(responseGradeTotalCreditLists.get(0).getNormalSelectionPoint());
				binding.gradeFreeSelectionCredit.setText(responseGradeTotalCreditLists.get(0).getFreeSelectionPoint());
			}
		});

		viewModel.responseGradeTermListLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ResponseGradeTermList>>() {
			@Override
			public void onChanged(ArrayList<ResponseGradeTermList> responseGradeTermLists) {
				// 그래프 그릴 공간
				List<Entry> entries = new ArrayList<>();
				ArrayList<String> xValues = new ArrayList<>();
				ArrayList<Float> yValues = new ArrayList<>();
				for(int i = 0; i < responseGradeTermLists.size(); i++) {
					Float y = Float.parseFloat(responseGradeTermLists.get(i).getGrdMarkAvg().replace(" ", ""));

					// 추후 포매팅
					xValues.add(responseGradeTermLists.get(i).getSchYear() + "년\n" + responseGradeTermLists.get(i).getSchTerm() + "학기");
					yValues.add(Float.parseFloat(responseGradeTermLists.get(i).getGrdMarkAvg().replace(" ", "")));

					entries.add(new Entry(i, y));
				}
				LineDataSet lineDataSet = new LineDataSet(entries, null);
				// 선 두께
				lineDataSet.setLineWidth(2);
				lineDataSet.setColor(getContext().getColor(R.color.mainBlue));
				// 동그라미
				lineDataSet.setDrawCircles(true);
				lineDataSet.setCircleRadius(4);
				lineDataSet.setCircleColor(getContext().getColor(R.color.mainBlue));
				// 동그라미 구멍
				lineDataSet.setDrawCircleHole(true);
				lineDataSet.setCircleHoleRadius(3);
				lineDataSet.setCircleHoleColor(getContext().getColor(R.color.white));
				// 데이터 값 표시
				/*
				lineDataSet.setValueTextSize(8);
				lineDataSet.setValueFormatter(new YAxisFormatter());
				lineDataSet.setValueTextColor(getContext().getColor(R.color.gray2));
				 */
				lineDataSet.setDrawValues(false);
				// 하이라이트 인디케이터
				lineDataSet.setDrawHighlightIndicators(false);

				LineData lineData = new LineData(lineDataSet);
				gradeGraph.setData(lineData);

				// 하단 x label
				XAxis xAxis = gradeGraph.getXAxis();
				xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
				xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
				xAxis.setTextSize(8);
				xAxis.setTextColor(getContext().getColor(R.color.gray2));
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					xAxis.setTypeface(getResources().getFont(R.font.roboto));
				} else {
					xAxis.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto.ttf"));
				}
				xAxis.setGridColor(getContext().getColor(R.color.gray2));
				xAxis.setDrawGridLines(false);
				xAxis.setDrawAxisLine(false);

				// 좌측 y label
				YAxis yLAxis = gradeGraph.getAxisLeft();
				yLAxis.setValueFormatter(new XAxisFormatter());
				yLAxis.setTextSize(10);
				yLAxis.setTextColor(getContext().getColor(R.color.gray2));
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					yLAxis.setTypeface(getResources().getFont(R.font.roboto));
				} else {
					yLAxis.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto.ttf"));
				}
				yLAxis.setAxisMinimum(0);
				yLAxis.setAxisMaximum(4.5f);
				yLAxis.setLabelCount(3);
				yLAxis.setDrawAxisLine(false);

				// 우측 y label
				YAxis yRAxis = gradeGraph.getAxisRight();
				yRAxis.setDrawLabels(true);
				yRAxis.setTextColor(getContext().getColor(R.color.white));
				yRAxis.setDrawAxisLine(false);
				yRAxis.setDrawGridLines(false);

				// 마커 설정
				MyMarkerView myMarkerView = new MyMarkerView(getContext(), R.layout.grade_all_marker_form);

				// 그래프 자체 설정
				gradeGraph.setMarker(myMarkerView);
				gradeGraph.setNoDataText("데이터 불러오는중");
				gradeGraph.setDoubleTapToZoomEnabled(false);
				gradeGraph.setDragEnabled(false);
				gradeGraph.setPinchZoom(false);
				gradeGraph.setDrawGridBackground(false);
				gradeGraph.setDescription(null);
				gradeGraph.setScaleEnabled(false);
				gradeGraph.setXAxisRenderer(new XAxisRenderer(gradeGraph.getViewPortHandler(), gradeGraph.getXAxis(), gradeGraph.getTransformer(YAxis.AxisDependency.LEFT)));
				gradeGraph.setExtraOffsets(10, 30, 10, 30);

				// legend 비활성화
				Legend legend = gradeGraph.getLegend();
				legend.setEnabled(false);

				gradeGraph.animateY(1000);
//				gradeGraph.invalidate();
			}
		});

        return binding.getRoot();
    }

	@Override
	public void onBackPressed() {
		gradeGraph.clearAnimation();
		homeLeftContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
		homeLeftContainer.getChildFragmentManager().popBackStackImmediate();
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		((HomeActivity) context).setOnBackPressedListner(this);
	}
}
