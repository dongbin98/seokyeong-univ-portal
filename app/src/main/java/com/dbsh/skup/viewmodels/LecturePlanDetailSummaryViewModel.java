package com.dbsh.skup.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.repository.PortalRepository;
import com.dbsh.skup.api.PortalApi;
import com.dbsh.skup.model.RequestLecturePlanBookData;
import com.dbsh.skup.model.RequestLecturePlanBookParameterData;
import com.dbsh.skup.model.RequestLecturePlanSummaryData;
import com.dbsh.skup.model.RequestLecturePlanSummaryParameterData;
import com.dbsh.skup.model.ResponseLecturePlanBook;
import com.dbsh.skup.model.ResponseLecturePlanBookList;
import com.dbsh.skup.model.ResponseLecturePlanSummary;
import com.dbsh.skup.model.ResponseLecturePlanSummaryMap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LecturePlanDetailSummaryViewModel extends ViewModel {

	public MutableLiveData<ResponseLecturePlanSummaryMap> responseLecturePlanSummaryMapLiveData = new MutableLiveData<>();
	public MutableLiveData<ArrayList<ResponseLecturePlanBookList>> responseLecturePlanBookListLiveData = new MutableLiveData<>();

	public PortalApi portalApi;

	public void getLecturePlanSummary(String token, String id, String subjCd, String clssNumb, String year, String term, String professorId) {
		PortalRepository portalRepository = PortalRepository.getInstance(token);
		portalApi = PortalRepository.getPortalApi();
		RequestLecturePlanSummaryParameterData parameter = new RequestLecturePlanSummaryParameterData(year, term, subjCd, clssNumb, professorId);
		portalApi.getLecturePlanSummaryData(new RequestLecturePlanSummaryData(
				"education.ucs.UCS_03100_T.SELECT_REPORT_MAIN",
				"AL",
				token,
				"common/selectOne",
				"UCS_03090_P",
				id,
				parameter
		)).enqueue(new Callback<ResponseLecturePlanSummary>() {
			@Override
			public void onResponse(Call<ResponseLecturePlanSummary> call, Response<ResponseLecturePlanSummary> response) {
				if (response.isSuccessful()) {
					if (response.body().getRtnStatus().equals("S")) {
						responseLecturePlanSummaryMapLiveData.setValue(response.body().getResponseLecturePlanSummaryMap());
					}
				}
			}

			@Override
			public void onFailure(Call<ResponseLecturePlanSummary> call, Throwable t) {
			}
		});
	}

	public void getLecturePlanBook(String token, String id, String subjCd, String clssNumb, String year, String term, String professorId) {
		PortalRepository portalRepository = PortalRepository.getInstance(token);
		portalApi = PortalRepository.getPortalApi();
		RequestLecturePlanBookParameterData parameter = new RequestLecturePlanBookParameterData(year, term, subjCd, clssNumb, professorId);
		portalApi.getLecturePlanBookData(new RequestLecturePlanBookData(
				"education.ucs.UCS_03100_T.SELECT_REPORT_BOOKINFO",
				"AL",
				token,
				"common/selectList",
				"UCS_03090_P",
				id,
				parameter
		)).enqueue(new Callback<ResponseLecturePlanBook>() {
			@Override
			public void onResponse(Call<ResponseLecturePlanBook> call, Response<ResponseLecturePlanBook> response) {
				if (response.isSuccessful()) {
					if (response.body().getRtnStatus().equals("S")) {
						responseLecturePlanBookListLiveData.setValue(response.body().getResponseLecturePlanBookLists());
					}
				}
			}

			@Override
			public void onFailure(Call<ResponseLecturePlanBook> call, Throwable t) {

			}
		});
	}
}
