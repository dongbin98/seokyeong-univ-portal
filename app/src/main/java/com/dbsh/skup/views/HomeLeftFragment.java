package com.dbsh.skup.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.HomeLeftFormBinding;
import com.dbsh.skup.viewmodels.HomeLeftViewModel;

public class HomeLeftFragment extends Fragment {

    private HomeLeftFormBinding binding;
    private HomeLeftViewModel viewModel;

	// this Fragment
	private Fragment HomeLeftFragment;

    // parent Fragment
    private HomeLeftContainer homeLeftContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* Data Binding */
        binding = DataBindingUtil.inflate(inflater, R.layout.home_left_form, container, false);
        viewModel = new HomeLeftViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        HomeLeftFragment = this;
        homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());

        Bundle bundle = new Bundle();
        bundle.putString("type", "left");

		// 출결 버튼
        binding.mainMenuAttendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeLeftContainer.pushFragment(HomeLeftFragment, new AttendanceFragment(), bundle);
            }
        });

		// 시간표 버튼
        binding.mainMenuTimetableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeLeftContainer.pushFragment(HomeLeftFragment, new TimeTableFragment(), bundle);
            }
        });

        // 성적 버튼
        binding.mainMenuGradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeLeftContainer.pushFragment(HomeLeftFragment, new GradeFragment(), null);
            }
        });

        // 강의 버튼
        binding.mainMenuLectureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
	            homeLeftContainer.pushFragment(HomeLeftFragment, new LecturePlanFragment(), null);
            }
        });

        // 장학 버튼
        binding.mainMenuScholarshipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
	            homeLeftContainer.pushFragment(HomeLeftFragment, new ScholarshipFragment(), null);
            }
        });

        // 등록 버튼
        binding.mainMenuRegistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				homeLeftContainer.pushFragment(HomeLeftFragment, new TuitionFragment(), null);
            }
        });

        // 졸업 버튼
        binding.mainMenuGraduateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
	            homeLeftContainer.pushFragment(HomeLeftFragment, new GraduateFragment(), null);
            }
        });

        // 학사일정 버튼
        binding.mainMenuPotenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url", "https://www.skuniv.ac.kr/academic_calendar");
                startActivity(intent);
            }
        });

        // 포탈 버튼
        binding.mainMenuQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url", "https://sportal.skuniv.ac.kr");
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
}