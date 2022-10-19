package com.dbsh.skup.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.SpinnerAdapter;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.TimetableFormBinding;
import com.dbsh.skup.model.ResponseLectureList;
import com.dbsh.skup.model.ResponseYearList;
import com.dbsh.skup.viewmodels.TimeTableViwModel;

import java.util.ArrayList;

public class TimeTableFragment extends Fragment implements OnBackPressedListener {

    private TimetableFormBinding binding;
    private TimeTableViwModel viewModel;

    // parent Fragment
    private HomeLeftContainer homeLeftContainer;

    String token;
    String id;
    String year;
    String term;

    Spinner timetableSpinner;
    Spinner timetableSpinner2;
    ImageButton timetableBtn;

    ArrayList<String> spinnerItem, spinnerItem2;

    UserData userData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* DataBinding */
        binding = DataBindingUtil.inflate(inflater, R.layout.timetable_form, container, false);
        binding.setLifecycleOwner(this);
        viewModel = new TimeTableViwModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();    // 바인딩 강제 즉시실행

        homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());

        userData = ((UserData) getActivity().getApplication());

        token = userData.getToken();
        id = userData.getId();
        year = userData.getSchYear();
        term = userData.getSchTerm();

        timetableSpinner = binding.timetableSpinner;
        timetableSpinner2 = binding.timetableSpinner2;
        timetableBtn = binding.timetableBtn;

        Toolbar mToolbar = binding.timetableToolbar;

        ((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("");

        spinnerItem = new ArrayList<>();
        for (ResponseYearList yearList : userData.getYearList()) {
            String value = yearList.getValue() + "년";

            if (!spinnerItem.contains(value)) {
                spinnerItem.add(value);
            }
        }

        spinnerItem2 = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            String value = i + "학기";

            if (!spinnerItem2.contains(value)) {
                spinnerItem2.add(value);
            }
        }

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext(), spinnerItem);
        timetableSpinner.setAdapter(spinnerAdapter);
        timetableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = spinnerItem.get(i).substring(0, 4);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        SpinnerAdapter spinnerAdapter2 = new SpinnerAdapter(getContext(), spinnerItem2);
        timetableSpinner2.setAdapter(spinnerAdapter2);
        timetableSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                term = spinnerItem2.get(i).substring(0, 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        for(int i = 0; i < spinnerItem.size(); i++) {
            if(spinnerItem.get(i).substring(0, 4).equals(year)) {
                timetableSpinner.setSelection(i);
            }
        }

        for(int i = 0; i < spinnerItem2.size(); i++) {
            if(spinnerItem2.get(i).substring(0, 1).equals(term)) {
                timetableSpinner2.setSelection(i);
            }
        }

        // 장학 조회버튼
        timetableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                timetableBtn.setClickable(false);
                viewModel.getLectureData(token, id, year, term);
            }
        });

//        timetableBtn.setClickable(false);
        viewModel.getLectureData(token, id, year, term);

        viewModel.lectureLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ResponseLectureList>>() {
            @Override
            public void onChanged(ArrayList<ResponseLectureList> responseLectureLists) {
                // 시간표 그려주는 부분
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
