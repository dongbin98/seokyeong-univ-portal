package com.dbsh.skup.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.client.PortalClient;
import com.dbsh.skup.api.PortalApi;
import com.dbsh.skup.dto.RequestScholarshipData;
import com.dbsh.skup.dto.RequestScholarshipParameterData;
import com.dbsh.skup.dto.ResponseScholar;
import com.dbsh.skup.dto.ResponseScholarList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScholarshipViewModel extends ViewModel {
    public MutableLiveData<ArrayList<ResponseScholarList>> scholarLiveData = new MutableLiveData<>();

    public PortalApi portalApi;

    public void getScholar(String token, String id, String year, String term) {
        PortalClient portalClient = PortalClient.getInstance(token);
        portalApi = PortalClient.getPortalApi();
        RequestScholarshipParameterData parameter = new RequestScholarshipParameterData(id, year, term, id);
        portalApi.getScholarshipData(new RequestScholarshipData(
                "education.uss.USS_01011_T.select_janghak",
                "AL",
                token,
                "common/selectList",
                "USS_02013_V",
                id,
                parameter)).enqueue(new Callback<ResponseScholar>() {
            @Override
            public void onResponse(Call<ResponseScholar> call, Response<ResponseScholar> response) {
                if (response.isSuccessful()) {
                    // 졸업생의 경우 response.body() == null
                    if (response.body().getRtnStatus().equals("S")) {
                        scholarLiveData.setValue(response.body().getResponseScholarLists());
                    } else {
						scholarLiveData.setValue(null);
                    }
                } else {
					scholarLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseScholar> call, Throwable t) {
                Log.d("Failure", t.getLocalizedMessage());
            }
        });
    }
}
