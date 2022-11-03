package com.dbsh.skup.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.repository.PortalRepository;
import com.dbsh.skup.api.PortalApi;
import com.dbsh.skup.model.RequestAttendanceData;
import com.dbsh.skup.model.RequestAttendanceDetailData;
import com.dbsh.skup.model.RequestAttendanceDetailParameterData;
import com.dbsh.skup.model.RequestAttendanceParameterData;
import com.dbsh.skup.model.ResponseAttendance;
import com.dbsh.skup.model.ResponseAttendanceDetail;
import com.dbsh.skup.model.ResponseAttendanceDetailList;
import com.dbsh.skup.model.ResponseAttendanceList;

import java.io.IOException;
import java.util.ArrayList;

import needle.Needle;
import needle.UiRelatedTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceViewModel extends ViewModel {

	public MutableLiveData<ResponseAttendanceList> attendanceLiveData = new MutableLiveData<>();
	public MutableLiveData<Integer> totalSizeLiveData = new MutableLiveData<>();

	public PortalApi portalApi;

	public void getAttendance(String token, String id, String year, String term) {
		PortalRepository portalRepository = PortalRepository.getInstance(token);
		portalApi = PortalRepository.getPortalApi();
		RequestAttendanceParameterData parameter = new RequestAttendanceParameterData(id, year, term, id);
		portalApi.getAttendanceData(new RequestAttendanceData(
				"education.ual.UAL_04004_T.select",
				"AL",
				token,
				"common/selectList",
				"UAL_04004_T",
				id,
				parameter)).enqueue(new Callback<ResponseAttendance>() {
			@Override
			public void onResponse(Call<ResponseAttendance> call, Response<ResponseAttendance> response) {
				if (response.isSuccessful()) {
					if (response.body().getRtnStatus().equals("S")) {
						// 졸업생의 경우 response.body() == null
						// System.out.println(response.body());
						ArrayList<ResponseAttendanceList> attendanceList = response.body().getResponseAttendanceLists();
						for (ResponseAttendanceList responseAttendanceList : attendanceList) {
							try {
								getAttendanceDetailData(token, id, year, term, responseAttendanceList.getSubjCd(), responseAttendanceList.getClssNumb(), responseAttendanceList, attendanceList.size());
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} else {	// 잘못된 파라미터 참조 case
						totalSizeLiveData.setValue(0);
						attendanceLiveData.setValue(null);
					}
				} else {		// null case
					totalSizeLiveData.setValue(0);
					attendanceLiveData.setValue(null);
				}
			}

			@Override
			public void onFailure(Call<ResponseAttendance> call, Throwable t) {
				//
			}
		});
	}

	public void getAttendanceDetailData(String token, String id, String year, String term, String cd, String numb, ResponseAttendanceList responseAttendanceList, int totalSize) throws IOException {
		Needle.onBackgroundThread().execute(new UiRelatedTask<ResponseAttendanceList>() {
			@Override
			protected ResponseAttendanceList doWork() {
				System.out.println("call getAttendanceDetailData");
				int percent = 0;
				double lectTime = 0.0;  // 수업 강의시간
				double fullTime = 0.0;  // 현재까지 수업시간 합계
				double absnTime = 0.0;  // 지각 시간

				PortalRepository portalRepository = PortalRepository.getInstance(token);
				portalApi = PortalRepository.getPortalApi();
				RequestAttendanceDetailParameterData parameter = new RequestAttendanceDetailParameterData(numb, year, term, id, cd);
				ResponseAttendanceDetail responseAttendanceDetail = null;
				try {
					responseAttendanceDetail = portalApi.getAttendanceDetailData(new RequestAttendanceDetailData(
							"education.ual.UAL_04004_T.select_attend_pop",
							"AL",
							token,
							"common/selectList",
							"UAL_04004_T",
							id,
							parameter)).execute().body();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (responseAttendanceDetail.getRtnStatus().equals("S")) {
					lectTime = getFullTime(responseAttendanceDetail.getResponseAttendanceDetailLists().get(0).getWeekTime());
					ArrayList<ResponseAttendanceDetailList> responseAttendanceDetailLists = responseAttendanceDetail.getResponseAttendanceDetailLists();
					for (ResponseAttendanceDetailList responseAttendanceDetailList : responseAttendanceDetailLists) {
						fullTime += lectTime;
						absnTime += Double.parseDouble(responseAttendanceDetailList.getAbsnTime());
					}
					percent = (int) ((fullTime - absnTime) / fullTime * 100);
					responseAttendanceList.setSubjTime(lectTime);
					responseAttendanceList.setAttendanceRate(percent);
				}
				return responseAttendanceList;
			}

			@Override
			protected void thenDoUiRelatedWork(ResponseAttendanceList responseAttendanceList) {
				totalSizeLiveData.setValue(totalSize);
				attendanceLiveData.setValue(responseAttendanceList);
			}
		});
	}

	public double getFullTime(String weekTime) {
		double time = 0.0;
		String[] times = weekTime.split(",");
		for (int i = 0; i < times.length; i++) {
			int divide = Integer.parseInt(times[i]);
			if(divide >= 1 && divide <= 12)
				time += 1.0;
			else
				time += 1.5;
		}
		return time;
	}
}
