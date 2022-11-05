package com.dbsh.skup.views;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.LinearLayoutManagerWrapper;
import com.dbsh.skup.adapter.SpinnerAdapter;
import com.dbsh.skup.adapter.TimetableAdapter;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.TimetableFormBinding;
import com.dbsh.skup.model.ResponseLectureList;
import com.dbsh.skup.model.ResponseYearList;
import com.dbsh.skup.viewmodels.TimeTableViewModel;

import java.util.ArrayList;
import java.util.List;

public class TimeTableFragment extends Fragment implements OnBackPressedListener {

    private TimetableFormBinding binding;
    private TimeTableViewModel viewModel;

    // parent Fragment
    private HomeLeftContainer homeLeftContainer;

    private String token;
    private String id;
    private String year;
    private String term;

    private int overtime;

    private Spinner timetableSpinner;
    private Spinner timetableSpinner2;
    private ImageButton timetableBtn;

    private ArrayList<String> spinnerItem, spinnerItem2;
    private ArrayList<TextView> timetableItems;
    private ArrayList<TextView> timetableIndexes;
    final int[] colors = {R.color.timetableitem1, R.color.timetableitem2, R.color.timetableitem3,
		    R.color.mainBlue, R.color.timetableitem4, R.color.timetableitem5, R.color.timetableitem6,
            R.color.mainYellow, R.color.purple_200, R.color.purple_500, R.color.teal_700};

    List<TimetableAdapter.TimetableItem> data;
    public TimetableAdapter adapter;
    RecyclerView timeList;

    UserData userData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* DataBinding */
        binding = DataBindingUtil.inflate(inflater, R.layout.timetable_form, container, false);
        viewModel = new TimeTableViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();    // 바인딩 강제 즉시실행

        overtime = 0;

        homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());

        userData = ((UserData) getActivity().getApplication());

        token = userData.getToken();
        id = userData.getId();
        year = userData.getSchYear();
        term = userData.getSchTerm();

        timetableSpinner = binding.timetableSpinner;
        timetableSpinner2 = binding.timetableSpinner2;
        timetableBtn = binding.timetableBtn;

        timetableItems = new ArrayList<>();
        timetableIndexes = new ArrayList<>();
        data = new ArrayList<>();

        timeList = binding.timetableRecyclerview;
        adapter = new TimetableAdapter(data);

        LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false);
        timeList.setLayoutManager(linearLayoutManagerWrapper);
        timeList.setAdapter(adapter);

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

        // 시간표 조회버튼
        timetableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timetableBtn.setClickable(false);
                for(TextView tv : timetableItems) {
                    binding.timetableConstraintlayout.removeView(tv);
                }
                overtime = 0;
                timetableItems.clear();
                viewModel.getLectureData(token, id, year, term);
            }
        });

        timetableBtn.setClickable(false);
        viewModel.getLectureData(token, id, year, term);

        viewModel.lectureLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ResponseLectureList>>() {
            @Override
            public void onChanged(ArrayList<ResponseLectureList> responseLectureLists) {
                if(responseLectureLists != null) {
                    int startY, endY, height, marginY;
                    String lecture, classroom, professor;
                    // 시간표 그려주는 부분
                    for (ResponseLectureList responseLectureList : responseLectureLists) {
                        // 강의시간이 존재하는 수업의 경우
	                    // OCU : 시작, 종료, 요일 정보 없음
                        if (responseLectureList.getLectureDay() != null) {
                            startY = getYtoTime(responseLectureList.getLectureStartTime());
                            endY = getYtoTime(responseLectureList.getLectureEndTime());

                            lecture = responseLectureList.getLectureName();
                            classroom = (responseLectureList.getClassroomName() != null ? "\nㆍ\n" + responseLectureList.getClassroomName() : "");
                            professor = (responseLectureList.getProfName() != null ? "\nㆍ\n" + responseLectureList.getProfName() : "");

                            TextView tv = new TextView(getContext());
                            tv.setText(lecture + classroom + professor);
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            tv.setTextSize(10);
	                        tv.setMaxLines((endY-startY) / 12);   // 12dp 당 한 줄로 잡음
                            tv.setTextColor(getContext().getColor(R.color.onlyWhite));
							// Text Style
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                tv.setTypeface(getResources().getFont(R.font.roboto), Typeface.BOLD);
                            } else {
                                tv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto.ttf"), Typeface.BOLD);
                            }
                            int colorSet = getRandomColor(responseLectureList.getLectureName(), colors.length);
                            tv.setBackgroundColor(getContext().getColor(colors[colorSet]));

                            height = int2dp(endY - startY);
                            marginY = int2dp(startY);

                            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(binding.timetableBodyMon.getWidth(), height);
                            lp.setMargins(0, marginY, 0, 0);
                            if (responseLectureList.getLectureDay().equals("1")) {
                                // 월요일
                                lp.topToTop = binding.timetableBodyMon.getId();
                                lp.leftToLeft = binding.timetableBodyMon.getId();
                                lp.rightToRight = binding.timetableBodyMon.getId();
                            } else if (responseLectureList.getLectureDay().equals("2")) {
                                // 화요일
                                lp.topToTop = binding.timetableBodyTue.getId();
                                lp.leftToLeft = binding.timetableBodyTue.getId();
                                lp.rightToRight = binding.timetableBodyTue.getId();
                            } else if (responseLectureList.getLectureDay().equals("3")) {
                                // 수요일
                                lp.topToTop = binding.timetableBodyWed.getId();
                                lp.leftToLeft = binding.timetableBodyWed.getId();
                                lp.rightToRight = binding.timetableBodyWed.getId();
                            } else if (responseLectureList.getLectureDay().equals("4")) {
                                // 목요일
                                lp.topToTop = binding.timetableBodyThu.getId();
                                lp.leftToLeft = binding.timetableBodyThu.getId();
                                lp.rightToRight = binding.timetableBodyThu.getId();
                            } else if (responseLectureList.getLectureDay().equals("5")) {
                                // 금요일
                                lp.topToTop = binding.timetableBodyFri.getId();
                                lp.leftToLeft = binding.timetableBodyFri.getId();
                                lp.rightToRight = binding.timetableBodyFri.getId();
                            }
                            tv.setLayoutParams(lp);
                            timetableItems.add(tv);
                            binding.timetableConstraintlayout.addView(tv);
                        } else {
                            data.add(new TimetableAdapter.TimetableItem(responseLectureList.getLectureName()));
                            adapter.notifyItemInserted(data.size());
                        }
                    }

                    if(overtime >= 1) {
                        // 6시 이후에 수업이 있는 경우
                        for(int i = 18; i < 21; i++) {
                            TextView index = new TextView(getContext());
                            index.setText(Integer.toString(i));
                            index.setTextColor(getContext().getColor(R.color.gray2));
                            index.setTextSize(16);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                index.setTypeface(getResources().getFont(R.font.roboto), Typeface.BOLD);
                            } else {
                                index.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto.ttf"), Typeface.BOLD);
                            }
                            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                            lp.setGravity(Gravity.CENTER);
                            lp.height = int2dp(48);
                            index.setLayoutParams(lp);
                            timetableIndexes.add(index);
                            binding.timetableIndex.addView(index);
                        }
                    } else {
                        // 6시 이후에 수업이 없는 경우
                        for(TextView index : timetableIndexes)
                            binding.timetableIndex.removeView(index);
                    }
                }
                timetableBtn.setClickable(true);
            }
        });

        return binding.getRoot();
    }
    @Override
    public void onBackPressed() {
        homeLeftContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
        homeLeftContainer.getChildFragmentManager().popBackStackImmediate();
        homeLeftContainer.popFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((HomeActivity) context).setOnBackPressedListner(this);
    }

    public int getYtoTime(String time) {
        int y;
        String[] div = time.split(":");
        // 한 시간에 48dp
        y = (Integer.parseInt(div[0]) - 9) * 48 + (int) ((Double.parseDouble(div[1]) / 60) * 48) + 10;
        if(y >= 442)
            overtime++;
        return y;
    }

	public int getRandomColor(String subjectName, int length) {
		int result = 0;
		char c;
		for(int i = 0; i < subjectName.length(); i++) {
			c = subjectName.charAt(i);
			result += ((int) c);
		}
		result += Integer.parseInt(userData.getId());
		return result % length;
	}

    public int int2dp(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }
}
