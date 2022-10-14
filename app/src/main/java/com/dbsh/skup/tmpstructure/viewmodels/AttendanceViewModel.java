package com.dbsh.skup.tmpstructure.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.tmpstructure.Service.PortalService;
import com.dbsh.skup.tmpstructure.api.PortalApi;
import com.dbsh.skup.tmpstructure.model.RequestAttendanceData;
import com.dbsh.skup.tmpstructure.model.RequestAttendanceDetailData;
import com.dbsh.skup.tmpstructure.model.RequestAttendanceDetailParameterData;
import com.dbsh.skup.tmpstructure.model.RequestAttendanceParameterData;
import com.dbsh.skup.tmpstructure.model.ResponseAttendance;
import com.dbsh.skup.tmpstructure.model.ResponseAttendanceDetail;
import com.dbsh.skup.tmpstructure.model.ResponseAttendanceDetailList;
import com.dbsh.skup.tmpstructure.model.ResponseAttendanceList;

import java.io.IOException;
import java.util.ArrayList;

import needle.Needle;
import needle.UiRelatedTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceViewModel extends ViewModel {

	public MutableLiveData<ResponseAttendanceList> attendanceLiveData = new MutableLiveData<>();
	public PortalApi portalApi;

	public void getAttendance(String token, String id, String year, String term) {
		System.out.println("call getAttendance");
		PortalService portalService = PortalService.getInstance(token);
		portalApi = PortalService.getPortalApi();
		RequestAttendanceParameterData parameter = new RequestAttendanceParameterData(id, year, term, id);
		ResponseAttendance responseAttendance = null;
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
						ArrayList<ResponseAttendanceList> attendanceList = response.body().getResponseAttendanceLists();
						for (ResponseAttendanceList responseAttendanceList : attendanceList) {
							try {
								getAttendanceDetailData(token, id, year, term, responseAttendanceList.getSubjCd(), responseAttendanceList.getClssNumb(), responseAttendanceList);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

			@Override
			public void onFailure(Call<ResponseAttendance> call, Throwable t) {
				//
			}
		});
	}

	public void getAttendanceDetailData(String token, String id, String year, String term, String cd, String numb, ResponseAttendanceList responseAttendanceList) throws IOException {
		Needle.onBackgroundThread().execute(new UiRelatedTask<ResponseAttendanceList>() {
			@Override
			protected ResponseAttendanceList doWork() {
				System.out.println("call getAttendanceDetailData");
				int percent = 0;
				double lectTime = 0.0;  // 수업 강의시간
				double fullTime = 0.0;  // 현재까지 수업시간 합계
				double absnTime = 0.0;  // 지각 시간

				PortalService portalService = PortalService.getInstance(token);
				portalApi = PortalService.getPortalApi();
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
					responseAttendanceList.setAttendanceRate(percent);
				}
				return responseAttendanceList;
			}

			@Override
			protected void thenDoUiRelatedWork(ResponseAttendanceList responseAttendanceList) {
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
