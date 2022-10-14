package com.dbsh.skup.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.Service.PortalService;
import com.dbsh.skup.api.PortalApi;
import com.dbsh.skup.model.RequestScholarshipData;
import com.dbsh.skup.model.RequestScholarshipParameterData;
import com.dbsh.skup.model.ResponseScholar;
import com.dbsh.skup.model.ResponseScholarList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScholarshipViewModel extends ViewModel {
    public MutableLiveData<ResponseScholarList> scholarLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> totalSizeLiveData = new MutableLiveData<>();

    public PortalApi portalApi;

    public void getScholar(String token, String id, String year, String term) {
        PortalService portalService = PortalService.getInstance(token);
        portalApi = PortalService.getPortalApi();
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
                    if (response.body().getRtnStatus().equals("S")) {
                        ArrayList<ResponseScholarList> responseScholarListArrayList = response.body().getResponseScholarLists();
                        totalSizeLiveData.setValue(responseScholarListArrayList.size());
                        for (ResponseScholarList responseScholarList : responseScholarListArrayList) {
                            scholarLiveData.setValue(responseScholarList);
                        }
                    } else {
                        totalSizeLiveData.setValue(0);
                        scholarLiveData.setValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseScholar> call, Throwable t) {
                Log.d("Failure", t.getLocalizedMessage());
            }
        });
    }
}
