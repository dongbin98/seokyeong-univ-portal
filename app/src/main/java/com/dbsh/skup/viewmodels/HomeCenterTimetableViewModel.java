package com.dbsh.skup.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.Service.PortalService;
import com.dbsh.skup.api.PortalApi;
import com.dbsh.skup.model.RequestLectureData;
import com.dbsh.skup.model.RequestLectureParameterData;
import com.dbsh.skup.model.ResponseLecture;
import com.dbsh.skup.model.ResponseLectureList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeCenterTimetableViewModel extends ViewModel {

	public MutableLiveData<ArrayList<ResponseLectureList>> lectureLiveData = new MutableLiveData<>();

	public PortalApi portalApi;

	public void getLectureData(String token, String id, String year, String term) {
		PortalService portalService = PortalService.getInstance(token);
		portalApi = PortalService.getPortalApi();
		RequestLectureParameterData parameter = new RequestLectureParameterData(id, year, term, id);
		portalApi.getLectureData(new RequestLectureData(
				"education.ucs.UCS_common.SELECT_TIMETABLE_2018",
				"AL",
				token,
				"common/selectList",
				"UAL_03333_T",
				id,
				parameter)).enqueue(new Callback<ResponseLecture>() {
			@Override
			public void onResponse(Call<ResponseLecture> call, Response<ResponseLecture> response) {
				if (response.isSuccessful()) {
					lectureLiveData.setValue(response.body().getLectureLists());
				}
			}

			@Override
			public void onFailure(Call<ResponseLecture> call, Throwable t) {
				Log.d("Failure", t.getLocalizedMessage());
			}
		});
	}
}
