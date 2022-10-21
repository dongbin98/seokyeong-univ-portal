package com.dbsh.skup.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.Service.PortalService;
import com.dbsh.skup.api.PortalApi;
import com.dbsh.skup.model.RequestGradeTermData;
import com.dbsh.skup.model.RequestGradeTermParameterData;
import com.dbsh.skup.model.RequestGradeTermSubjectData;
import com.dbsh.skup.model.RequestGradeTermSubjectParameterData;
import com.dbsh.skup.model.ResponseGradeTerm;
import com.dbsh.skup.model.ResponseGradeTermList;
import com.dbsh.skup.model.ResponseGradeTermSubject;
import com.dbsh.skup.model.ResponseGradeTermSubjectList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradeTermViewModel extends ViewModel {

	public MutableLiveData<ArrayList<ResponseGradeTermList>> responseGradeTermListLiveData = new MutableLiveData<>();
	public MutableLiveData<ArrayList<ResponseGradeTermSubjectList>> responseGradeTermSubjectLiveData = new MutableLiveData<>();
 	public PortalApi portalApi;

	public void getGradeTerm(String token, String id) {
		PortalService portalService = PortalService.getInstance(token);
		portalApi = PortalService.getPortalApi();
		RequestGradeTermParameterData parameter = new RequestGradeTermParameterData(id, id);
		portalApi.getGradeTerm(new RequestGradeTermData(
				"education.usc.USC_09001_V.select",
				"AL",
				token,
				"common/selectList",
				"USC_09001_V",
				id,
				parameter
		)).enqueue(new Callback<ResponseGradeTerm>() {
			@Override
			public void onResponse(Call<ResponseGradeTerm> call, Response<ResponseGradeTerm> response) {
				if (response.isSuccessful()) {
					if (response.body().getRtnStatus().equals("S")) {
						responseGradeTermListLiveData.setValue(response.body().getResponseGradeTermLists());
					}
				}
			}

			@Override
			public void onFailure(Call<ResponseGradeTerm> call, Throwable t) {

			}
		});
	}

	public void getGradeTermSubject(String token, String id, String year, String term) {
		PortalService portalService = PortalService.getInstance(token);
		portalApi = PortalService.getPortalApi();
		RequestGradeTermSubjectParameterData parameter = new RequestGradeTermSubjectParameterData(id, year, term, id);
		portalApi.getGradeTermSubject(new RequestGradeTermSubjectData(
				"education.usc.USC_09001_V.select_sub",
				"AL",
				token,
				"common/selectList",
				"USC_09001_V",
				id,
				parameter
		)).enqueue(new Callback<ResponseGradeTermSubject>() {
			@Override
			public void onResponse(Call<ResponseGradeTermSubject> call, Response<ResponseGradeTermSubject> response) {
				if (response.isSuccessful()) {
					if (response.body().getRtnStatus().equals("S")) {
						responseGradeTermSubjectLiveData.setValue(response.body().getResponseGradeTermSubjectLists());
					} else {
						responseGradeTermSubjectLiveData.setValue(null);
					}
				} else {
					responseGradeTermSubjectLiveData.setValue(null);
				}
			}

			@Override
			public void onFailure(Call<ResponseGradeTermSubject> call, Throwable t) {

			}
		});
	}
}
