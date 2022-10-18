package com.dbsh.skup.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.AttendanceDetailAdapter;
import com.dbsh.skup.adapter.LinearLayoutManagerWrapper;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.AttendanceDetailFormBinding;
import com.dbsh.skup.model.ResponseAttendanceDetailList;
import com.dbsh.skup.viewmodels.AttendanceDetailViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import me.bastanfar.semicirclearcprogressbar.SemiCircleArcProgressBar;

public class AttendanceDetailFragment extends Fragment implements OnBackPressedListener {

    private AttendanceDetailFormBinding binding;
    private AttendanceDetailViewModel viewModel;

	// parent Fragment
	private HomeCenterContainer homeCenterContainer;
    private HomeLeftContainer homeLeftContainer;

    SemiCircleArcProgressBar progressBar;
    TextView attendance_subj_toolbar, attendance_detail_percent;
	ValueAnimator animator;

    String token;
    String id;
    String year;
    String term;
    String cd;
    String numb;
    String type;

    String title;
    int percent;
    double time;

    int totalCount, attendanceCount, lateCount, absentCount;

    List<AttendanceDetailAdapter.AttendanceDetailItem> data;
    public AttendanceDetailAdapter adapter;
    RecyclerView attendanceDetailList;

	UserData userData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.inflate(inflater, R.layout.attendance_detail_form, container, false);
		binding.setLifecycleOwner(this);
		viewModel = new AttendanceDetailViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();	// 바인딩 강제 즉시실행

		homeCenterContainer = ((HomeCenterContainer) this.getParentFragment());
		userData = ((UserData) getActivity().getApplication());

        data = new ArrayList<>();

        totalCount = 0;
        attendanceCount = 0;
        lateCount = 0;
        absentCount = 0;

		if(getArguments() != null) {
			cd = getArguments().getString("CD");
			numb = getArguments().getString("NUMB");
			title = getArguments().getString("TITLE");
			percent = getArguments().getInt("PERCENT", 0);
			time = getArguments().getDouble("TIME", time);

			token = userData.getToken();
			id = userData.getId();
			year = getArguments().getString("YEAR");
			term = getArguments().getString("TERM");
            type = getArguments().getString("type");
		}

        if(type.equals("left"))
            homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());
        else
            homeCenterContainer = ((HomeCenterContainer) this.getParentFragment());

        attendance_subj_toolbar = binding.attendanceSubjToolbar;
        attendance_subj_toolbar.setText(title);

        attendance_detail_percent = binding.attendanceDetailPercent;

        progressBar = binding.attendanceHalfProgressbar;
		// 기존의 프로그레스바 값 설정
//        if (percent == 100) {
//            progressBar.setProgressBarColor(getColor(R.color.mainBlue));
//        } else if (percent >= 75 && percent < 100) {
//            progressBar.setProgressBarColor(getColor(R.color.mainYellow));
//        } else {
//            progressBar.setProgressBarColor(getColor(R.color.mainRed));
//        }
//	      attendance_detail_percent.setText(percent + "%");
//        progressBar.setPercent(percent);

	    // 애니메이션 적용
	    setAnimation(progressBar, attendance_detail_percent, percent);


        Toolbar mToolbar = binding.attendanceToolbar;

		((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
		((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		((HomeActivity) getActivity()).getSupportActionBar().setTitle("");

        attendanceDetailList = binding.attendanceDetailRecyclerview;
        adapter = new AttendanceDetailAdapter(data);

	    LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false);
        attendanceDetailList.setLayoutManager(linearLayoutManagerWrapper);
        attendanceDetailList.setAdapter(adapter);

        viewModel.getAttendanceDetailData(token, id, year, term, cd, numb);

        viewModel.attendanceDetailLiveData.observe(binding.getLifecycleOwner(), new Observer<ResponseAttendanceDetailList>() {
            @Override
            public void onChanged(ResponseAttendanceDetailList responseAttendanceDetailList) {
                double absnTime = Double.parseDouble(responseAttendanceDetailList.getAbsnTime());
                if (absnTime == 0) {
                    attendanceCount++;
                    data.add(new AttendanceDetailAdapter.AttendanceDetailItem(
                            "출석", "출석", responseAttendanceDetailList.getCheckDateNm(), "출석했습니다."));
                } else if (absnTime < time) {
                    lateCount++;
                    data.add(new AttendanceDetailAdapter.AttendanceDetailItem(
                            "지각", "지각", responseAttendanceDetailList.getCheckDateNm(), absnTime + "시간 지각했습니다."));
                } else {
                    absentCount++;
                    data.add(new AttendanceDetailAdapter.AttendanceDetailItem(
                            "결석", "결석", responseAttendanceDetailList.getCheckDateNm(), "결석했습니다"));
                }
                binding.attendanceDetailAtteCnt.setText(Integer.toString(attendanceCount));
                binding.attendanceDetailLateCnt.setText(Integer.toString(lateCount));
                binding.attendanceDetailAbsnCnt.setText(Integer.toString(absentCount));

                data.sort(new Comparator<AttendanceDetailAdapter.AttendanceDetailItem>() {
                    @Override
                    public int compare(AttendanceDetailAdapter.AttendanceDetailItem data, AttendanceDetailAdapter.AttendanceDetailItem t1) {
                        int result = 1;
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                        Date dateDate = null;
                        Date t1Date = null;

                        try {
                            dateDate = format.parse(data.getDate());
                            t1Date = format.parse(t1.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        int compare = dateDate.compareTo(t1Date);
                        if(compare >= 0)
                            result = -1;
                        return result;
                    }
                });
	            adapter.notifyItemInserted(data.size());
            }
        });
		return binding.getRoot();
    }

	private void setAnimation(final SemiCircleArcProgressBar progressBar, final TextView textView, final int percent) {
		animator = ValueAnimator.ofInt(0, percent).setDuration(1000);

		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				if ((int) valueAnimator.getAnimatedValue() == 100) {
		            progressBar.setProgressBarColor(getContext().getColor(R.color.mainBlue));
		        } else if ((int) valueAnimator.getAnimatedValue() >= 75 && (int) valueAnimator.getAnimatedValue() < 100) {
		            progressBar.setProgressBarColor(getContext().getColor(R.color.mainYellow));
		        } else {
		            progressBar.setProgressBarColor(getContext().getColor(R.color.mainRed));
		        }
				textView.setText((int) valueAnimator.getAnimatedValue() + "%");
				progressBar.setPercent((int) valueAnimator.getAnimatedValue());
			}
		});
		animator.start();
	}

	@Override
	public void onBackPressed() {
		animator.cancel();
        if(type.equals("center")) {
            homeCenterContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
            homeCenterContainer.getChildFragmentManager().popBackStackImmediate();
        } else {
            homeLeftContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
            homeLeftContainer.getChildFragmentManager().popBackStackImmediate();
        }
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		((HomeActivity)context).setOnBackPressedListner(this);
	}
}
