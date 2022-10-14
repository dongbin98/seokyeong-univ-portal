package com.dbsh.skup.tmpstructure.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.HomeTmpCenterTimetableFormBinding;
import com.dbsh.skup.tmpstructure.data.UserData;
import com.dbsh.skup.tmpstructure.model.ResponseLectureDetail;
import com.dbsh.skup.tmpstructure.viewmodels.HomeCenterTimetableViewModel;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeCenterTimetableFragment extends Fragment {

	HomeTmpCenterTimetableFormBinding binding;
	HomeCenterTimetableViewModel viewModel;

    TableLayout card2_timetableTodayTable;
    TextView card2_today;
    ArrayList<String> todayList;
    Calendar calendar = Calendar.getInstance();
    UserData userData;

    String token;
    String id;
    String year;
    String term;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		/* Data Binding */
		binding = DataBindingUtil.inflate(inflater, R.layout.home_tmp_center_timetable_form, container, false);
		viewModel = new HomeCenterTimetableViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();

        userData = ((UserData) getActivity().getApplication());

        token = userData.getToken();
        id = userData.getId();
        year = userData.getSchYear();
        term = userData.getSchTerm();
        todayList = new ArrayList<String>();

        card2_today = binding.card2Today;
        card2_timetableTodayTable = binding.card2TimetableTodayTable;

	    todayList = getTimetable(year, term);

	    switch (calendar.get(Calendar.DAY_OF_WEEK) - 1) {
		    case 1:
			    card2_today.setText("월요일 시간표");
			    break;
		    case 2:
			    card2_today.setText("화요일 시간표");
			    break;
		    case 3:
			    card2_today.setText("수요일 시간표");
			    break;
		    case 4:
			    card2_today.setText("목요일 시간표");
			    break;
		    case 5:
			    card2_today.setText("금요일 시간표");
			    break;
		    default:
			    card2_today.setText("주말 시간표");
			    break;
	    }
	    TableRow.LayoutParams params = new TableRow.LayoutParams(
			    ViewGroup.LayoutParams.WRAP_CONTENT,
			    ViewGroup.LayoutParams.WRAP_CONTENT
	    );
	    for(int i = 0; i < todayList.size(); i++) {
		    TableRow tableRow = new TableRow(getActivity());
		    tableRow.setLayoutParams(params);
		    TextView textView = new TextView(getActivity());
		    textView.setText(todayList.get(i));
		    textView.setTextColor(getActivity().getColor(R.color.black));
		    textView.setPadding(0,0,0,20);
		    tableRow.addView(textView);
		    card2_timetableTodayTable.addView(tableRow);
	    }
        return binding.getRoot();
    }

    public ArrayList<String> getTimetable(String year, String term) {
	    for (ResponseLectureDetail lectureDetail : userData.getLectureDatas()) {
			if(lectureDetail.getYear().equals(year) && lectureDetail.getTerm().equals(term)) {
				if(!(lectureDetail.getLectureDay() == null) && lectureDetail.getLectureDay().equals(Integer.toString(calendar.get(Calendar.DAY_OF_WEEK) - 1))) {
					todayList.add(lectureDetail.getLectureStartTime() + " ~ " + lectureDetail.getLectureEndTime() + " " + lectureDetail.getLectureName());
				}
			}
	    }
	    return todayList;
    }
}
