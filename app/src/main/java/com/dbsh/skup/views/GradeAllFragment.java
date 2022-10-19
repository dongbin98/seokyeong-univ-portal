package com.dbsh.skup.views;

import android.content.Context;
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
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.GradeAllFormBinding;
import com.dbsh.skup.model.ResponseGradeTermList;
import com.dbsh.skup.model.ResponseGradeTotalCreditList;
import com.dbsh.skup.model.ResponseGradeTotalMap;
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
				binding.gradeMajorCredit.setText(responseGradeTotalCreditLists.get(0).getMajorPoint());
				binding.gradeLiberalCredit.setText(responseGradeTotalCreditLists.get(0).getLiberalPoint());
				binding.gradeEtcCredit.setText(responseGradeTotalCreditLists.get(0).getEtcPoint());
			}
		});

		viewModel.responseGradeTermListLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ResponseGradeTermList>>() {
			@Override
			public void onChanged(ArrayList<ResponseGradeTermList> responseGradeTermLists) {
				// 그래프 그릴 공간
				List<Entry> entries = new ArrayList<>();
				ArrayList<String> xValues = new ArrayList<>();
				for(int i = 0; i < responseGradeTermLists.size(); i++) {
					xValues.add(responseGradeTermLists.get(i).getSchYear() + "-" + responseGradeTermLists.get(i).getSchTerm());
					entries.add(new Entry(i, Float.parseFloat(responseGradeTermLists.get(i).getGrdMarkAvg().replace(" ", ""))));
				}

				LineDataSet lineDataSet = new LineDataSet(entries, null);
				// 선 두께
				lineDataSet.setLineWidth(1);
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
				lineDataSet.setValueTextSize(8);
				lineDataSet.setValueTextColor(getContext().getColor(R.color.gray2));
				// 하이라이트 인디케이터
				lineDataSet.setDrawHighlightIndicators(false);

				LineData lineData = new LineData(lineDataSet);
				gradeGraph.setData(lineData);

				XAxis xAxis = gradeGraph.getXAxis();
				xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
				xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
				xAxis.setTextSize(8);
				xAxis.setGranularity(1f);
				xAxis.setGranularityEnabled(true);
				xAxis.setTextColor(getContext().getColor(R.color.gray2));
				xAxis.setDrawAxisLine(false);
				xAxis.setDrawGridLines(false);

				YAxis yLAxis = gradeGraph.getAxisLeft();
				yLAxis.setTextSize(8);
				yLAxis.setTextColor(getContext().getColor(R.color.gray2));
				yLAxis.setAxisMinimum(0);
				yLAxis.setAxisMaximum(4.5f);
				yLAxis.setLabelCount(3);
				yLAxis.setSpaceMin(10);
				yLAxis.setDrawAxisLine(false);

				YAxis yRAxis = gradeGraph.getAxisRight();
				yRAxis.setDrawLabels(false);
				yRAxis.setDrawAxisLine(false);
				yRAxis.setDrawGridLines(false);

				gradeGraph.setNoDataText("데이터 불러오는중");
				gradeGraph.setDoubleTapToZoomEnabled(false);
				gradeGraph.setDragEnabled(false);
				gradeGraph.setPinchZoom(false);
				gradeGraph.setDrawGridBackground(false);
				gradeGraph.setDescription(null);
				gradeGraph.setScaleEnabled(false);
				gradeGraph.setExtraOffsets(10, 10, 10, 10);
				gradeGraph.setPadding(10, 10, 10, 10);

				Legend legend = gradeGraph.getLegend();
				legend.setEnabled(false);

				gradeGraph.invalidate();
			}
		});

        return binding.getRoot();
    }

	@Override
	public void onBackPressed() {
		homeLeftContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
		homeLeftContainer.getChildFragmentManager().popBackStackImmediate();
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		((HomeActivity) context).setOnBackPressedListner(this);
	}
}
