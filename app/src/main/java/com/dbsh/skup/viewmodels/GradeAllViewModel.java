package com.dbsh.skup.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.client.PortalClient;
import com.dbsh.skup.api.PortalApi;
import com.dbsh.skup.model.RequestGradeTermData;
import com.dbsh.skup.model.RequestGradeTermParameterData;
import com.dbsh.skup.model.RequestGradeTotalCreditData;
import com.dbsh.skup.model.RequestGradeTotalCreditParameterData;
import com.dbsh.skup.model.RequestGradeTotalData;
import com.dbsh.skup.model.RequestGradeTotalParameterData;
import com.dbsh.skup.model.ResponseGradeTerm;
import com.dbsh.skup.model.ResponseGradeTermList;
import com.dbsh.skup.model.ResponseGradeTotal;
import com.dbsh.skup.model.ResponseGradeTotalCredit;
import com.dbsh.skup.model.ResponseGradeTotalCreditList;
import com.dbsh.skup.model.ResponseGradeTotalMap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradeAllViewModel extends ViewModel {

	public MutableLiveData<ArrayList<ResponseGradeTotalCreditList>> responseGradeTotalCreditListLiveData = new MutableLiveData<>();
	public MutableLiveData<ResponseGradeTotalMap> responseGradeTotalMapLiveData = new MutableLiveData<>();
	public MutableLiveData<ArrayList<ResponseGradeTermList>> responseGradeTermListLiveData = new MutableLiveData<>();

	public PortalApi portalApi;

	public void getGradeTotal(String token, String id) {
		PortalClient portalClient = PortalClient.getInstance(token);
		portalApi = PortalClient.getPortalApi();
		RequestGradeTotalParameterData parameter = new RequestGradeTotalParameterData(id, id);
		portalApi.getGradeTotal(new RequestGradeTotalData(
				"education.usc.USC_09001_V.select01",
				"AL",
				token,
				"common/selectOne",
				"USC_09001_V",
				id,
				parameter
		)).enqueue(new Callback<ResponseGradeTotal>() {
			@Override
			public void onResponse(Call<ResponseGradeTotal> call, Response<ResponseGradeTotal> response) {
				if(response.isSuccessful()) {
					if(response.body().getRtnStatus().equals("S")) {
						responseGradeTotalMapLiveData.setValue(response.body().getResponseGradeTotalMap());
					}
				}
			}

			@Override
			public void onFailure(Call<ResponseGradeTotal> call, Throwable t) {

			}
		});
	}

	public void getGradeTotalCredit(String token, String id) {
		PortalClient portalClient = PortalClient.getInstance(token);
		portalApi = PortalClient.getPortalApi();
		RequestGradeTotalCreditParameterData parameter = new RequestGradeTotalCreditParameterData(id, id);
		portalApi.getGradeTotalCredit(new RequestGradeTotalCreditData(
				"education.usc.USC_09001_V.select02",
				"AL",
				token,
				"common/selectList",
				"USC_09001_V",
				id,
				parameter
		)).enqueue(new Callback<ResponseGradeTotalCredit>() {
			@Override
			public void onResponse(Call<ResponseGradeTotalCredit> call, Response<ResponseGradeTotalCredit> response) {
				if (response.isSuccessful()) {
					if (response.body().getRtnStatus().equals("S")) {
						responseGradeTotalCreditListLiveData.setValue(response.body().getResponseGradeTotalCreditLists());
					}
				}
			}

			@Override
			public void onFailure(Call<ResponseGradeTotalCredit> call, Throwable t) {

			}
		});
	}

	public void getGradeTerm(String token, String id) {
		PortalClient portalClient = PortalClient.getInstance(token);
		portalApi = PortalClient.getPortalApi();
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
}
