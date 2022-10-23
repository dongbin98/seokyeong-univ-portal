package com.dbsh.skup.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.Service.PortalService;
import com.dbsh.skup.api.PortalApi;
import com.dbsh.skup.model.RequestLecturePlanWeekData;
import com.dbsh.skup.model.RequestLecturePlanWeekParameterData;
import com.dbsh.skup.model.ResponseLecturePlanWeek;
import com.dbsh.skup.model.ResponseLecturePlanWeekList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LecturePlanDetailWeekViewModel extends ViewModel {

    public MutableLiveData<ArrayList<ResponseLecturePlanWeekList>> responseLecturePlanWeekListLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> totalSizeLiveData = new MutableLiveData<>();

    public PortalApi portalApi;

    public void getLecturePlanWeek(String token, String id, String subjCd, String clssNumb, String year, String term, String professorId) {
        PortalService portalService = PortalService.getInstance(token);
        portalApi = PortalService.getPortalApi();
        RequestLecturePlanWeekParameterData parameter = new RequestLecturePlanWeekParameterData(year, term, subjCd, clssNumb, professorId);
        portalApi.getLecturePlanWeekData(new RequestLecturePlanWeekData(
                "education.ucs.UCS_03100_T.SELECT_REPORT_CLASSPLAN",
                "AL",
                token,
                "common/selectList",
                "UCS_03090_P",
                id,
                parameter
        )).enqueue(new Callback<ResponseLecturePlanWeek>() {
            @Override
            public void onResponse(Call<ResponseLecturePlanWeek> call, Response<ResponseLecturePlanWeek> response) {
                if (response.isSuccessful()) {
                    if (response.body().getRtnStatus().equals("S")) {
                        ArrayList<ResponseLecturePlanWeekList> responseLecturePlanWeekLists = response.body().getResponseLecturePlanWeekLists();
                        totalSizeLiveData.setValue(responseLecturePlanWeekLists.size());
                        responseLecturePlanWeekListLiveData.setValue(responseLecturePlanWeekLists);
                    } else {    // 잘못된 파라미터 참조 case
                        totalSizeLiveData.setValue(0);
                        responseLecturePlanWeekListLiveData.setValue(null);
                    }
                } else {        // null case
                    totalSizeLiveData.setValue(0);
                    responseLecturePlanWeekListLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseLecturePlanWeek> call, Throwable t) {

            }
        });
    }
}
