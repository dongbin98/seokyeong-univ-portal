package com.dbsh.skup.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.HttpUrlConnector;
import com.dbsh.skup.R;
import com.dbsh.skup.user.LectureInfo;
import com.dbsh.skup.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeCenterTimetableFragment extends Fragment {
    private static final String timetableURL = "https://sportal.skuniv.ac.kr/sportal/common/selectList.sku";
    TableLayout card2_timetableTodayTable;
    TextView card2_today;
    ArrayList<String> todayList;
    Calendar cal = Calendar.getInstance();
    User user;
//    NetworkTask networkTask;
    String token;
    String id;
    String year;
    String term;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_center_timetable_form, container, false);
        user = ((User) getActivity().getApplication());

        token = user.getToken();
        id = user.getId();
        year = user.getSchYear();
        term = user.getSchTerm();
        todayList = new ArrayList<String>();

        card2_today = (TextView) rootView.findViewById(R.id.card2_today);
        card2_timetableTodayTable = (TableLayout) rootView.findViewById(R.id.card2_timetableTodayTable);

	    todayList = getTimetable(year, term);

	    switch (cal.get(Calendar.DAY_OF_WEEK) - 1) {
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

//        networkTask = new NetworkTask(token, id, year, term);
//        networkTask.execute();

        return rootView;
    }

//    public class NetworkTask extends AsyncTask<Void, Void, ArrayList<String>> {
//        String token;
//        String id;
//        String year;
//        String term;
//        ArrayList<String> result;
//
//        public NetworkTask(String token, String id, String year, String term) {
//            this.id = id;
//            this.token = token;
//            this.year = year;
//            this.term = term;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected ArrayList<String> doInBackground(Void... params) {
//            result = getTimetable(token, id, year, term);
//            return result;
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//        }
//        @Override
//        protected void onPostExecute(ArrayList<String> result) {
//            switch (cal.get(Calendar.DAY_OF_WEEK) - 1) {
//                case 1:
//                    card2_today.setText("월요일 시간표");
//                    break;
//                case 2:
//                    card2_today.setText("화요일 시간표");
//                    break;
//                case 3:
//                    card2_today.setText("수요일 시간표");
//                    break;
//                case 4:
//                    card2_today.setText("목요일 시간표");
//                    break;
//                case 5:
//                    card2_today.setText("금요일 시간표");
//                    break;
//                default:
//                    card2_today.setText("주말 시간표");
//                    break;
//            }
//            TableRow.LayoutParams params = new TableRow.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            );
//            for(int i = 0; i < result.size(); i++) {
//                TableRow tableRow = new TableRow(getActivity());
//                tableRow.setLayoutParams(params);
//                TextView textView = new TextView(getActivity());
//                textView.setText(result.get(i));
//                textView.setTextColor(getActivity().getColor(R.color.black));
//                textView.setPadding(0,0,0,20);
//                tableRow.addView(textView);
//                card2_timetableTodayTable.addView(tableRow);
//            }
//        }
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//    }

    public ArrayList<String> getTimetable(String year, String term) {

	    for (LectureInfo lectureInfo : user.getLectureInfos()) {
			if(lectureInfo.getYear().equals(year) && lectureInfo.getTerm().equals(term)) {
				if(!lectureInfo.getLectureDay().equals("null")) {
					todayList.add(lectureInfo.getLectureStartTime() + " ~ " + lectureInfo.getLectureEndTime() + " " + lectureInfo.getLectureName());
				}
			}
	    }

//        try {
//            JSONObject payload = new JSONObject();
//            JSONObject parameter = new JSONObject();
//
//            parameter.put("LECT_YEAR", year);
//            parameter.put("LECT_TERM", term);
//            parameter.put("ID", id);
//            parameter.put("STU_NO", id);
//            try {
//                payload.put("MAP_ID", "education.ucs.UCS_common.SELECT_TIMETABLE_2018");
//                payload.put("SYS_ID", "AL");
//                payload.put("accessToken", token);
//                payload.put("parameter", parameter);
//                payload.put("path", "common/selectlist");
//                payload.put("programID", "UAL_03333_T");
//                payload.put("userID", id);
//
//            } catch (JSONException exception) {
//                exception.printStackTrace();
//            }
//
//            JSONObject response = HttpUrlConnector.getInstance().getResponseWithToken(timetableURL, payload, token);
//
//            if(response.get("RTN_STATUS").toString().equals("S")) {
//                JSONArray jsonArray = response.getJSONArray("LIST");
//                int count = Integer.parseInt(response.get("COUNT").toString());
//
//                for (int i = 0; i < count; i++) {
//                    if(jsonArray.getJSONObject(i).get("CLASSDAY").toString().equals("null")) {
//                        // timetable에서 시간이 null인 경우 시간표에 안넣음 (졸업논문및시험 해당)
//                        //todayList.add(jsonArray.getJSONObject(i).get("CLASS_NAME").toString());
//                    }
//                    else {
//                        if (Integer.parseInt(jsonArray.getJSONObject(i).get("CLASSDAY").toString()) == cal.get(Calendar.DAY_OF_WEEK) - 1) {
//                            todayList.add(jsonArray.getJSONObject(i).get("CLASSHOUR_START_TIME").toString() + " ~ " +
//                                    jsonArray.getJSONObject(i).get("CLASSHOUR_END_TIME").toString() + "   " +
//                                    jsonArray.getJSONObject(i).get("CLASS_NAME").toString());
//                        }
//                    }
//                }
//            }
//            return todayList;
//        } catch (JSONException exception) {
//            exception.printStackTrace();
//            return null;
//        }
	    return todayList;
    }
}
