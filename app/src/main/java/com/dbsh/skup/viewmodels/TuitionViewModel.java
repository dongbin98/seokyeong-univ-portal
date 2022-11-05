package com.dbsh.skup.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.repository.PortalRepository;
import com.dbsh.skup.api.PortalApi;
import com.dbsh.skup.model.RequestTuitionData;
import com.dbsh.skup.model.RequestTuitionParameterData;
import com.dbsh.skup.model.ResponseTuition;
import com.dbsh.skup.model.ResponseTuitionMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TuitionViewModel extends ViewModel {

	public MutableLiveData<ResponseTuitionMap> tuitionLiveData = new MutableLiveData<>();
	public PortalApi portalApi;

	public void getTuition(String token, String id, String year, String term) {
		PortalRepository portalRepository = PortalRepository.getInstance(token);
		portalApi = PortalRepository.getPortalApi();
		RequestTuitionParameterData parameter = new RequestTuitionParameterData(id, year, term, id);
		portalApi.getTuitionData(new RequestTuitionData(
				"education.urg.URG_02012_V.SELECT",
				"AL",
				token,
				"common/selectOne",
				"URG_02012_V",
				id,
				parameter)).enqueue(new Callback<ResponseTuition>() {
			@Override
			public void onResponse(Call<ResponseTuition> call, Response<ResponseTuition> response) {
				// 졸업생의 경우 response.body() == null
				// System.out.println(response.body());
				if (response.isSuccessful()) {
					tuitionLiveData.setValue(response.body().getMap());
				}
			}

			@Override
			public void onFailure(Call<ResponseTuition> call, Throwable t) {
				Log.d("Failure", t.getLocalizedMessage());
			}
		});
	}
}
