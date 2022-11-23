package com.dbsh.skup.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.client.PortalClient;
import com.dbsh.skup.api.PortalApi;
import com.dbsh.skup.dto.RequestAttendanceDetailData;
import com.dbsh.skup.dto.RequestAttendanceDetailParameterData;
import com.dbsh.skup.dto.ResponseAttendanceDetail;
import com.dbsh.skup.dto.ResponseAttendanceDetailList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceDetailViewModel extends ViewModel {

    public MutableLiveData<ArrayList<ResponseAttendanceDetailList>> attendanceDetailLiveData = new MutableLiveData<>();
    public PortalApi portalApi;

    public void getAttendanceDetailData(String token, String id, String year, String term, String cd, String numb) {
        System.out.println("call getAttendanceDetailData");

        PortalClient portalClient = PortalClient.getInstance(token);
        portalApi = PortalClient.getPortalApi();
        RequestAttendanceDetailParameterData parameter = new RequestAttendanceDetailParameterData(numb, year, term, id, cd);
        portalApi.getAttendanceDetailData(new RequestAttendanceDetailData(
                    "education.ual.UAL_04004_T.select_attend_pop",
                    "AL",
                    token,
                    "common/selectList",
                    "UAL_04004_T",
                    id,
                    parameter)).enqueue(new Callback<ResponseAttendanceDetail>() {
            @Override
            public void onResponse(Call<ResponseAttendanceDetail> call, Response<ResponseAttendanceDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body().getRtnStatus().equals("S")) {
                        attendanceDetailLiveData.setValue(response.body().getResponseAttendanceDetailLists());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAttendanceDetail> call, Throwable t) {
                //
            }
        });
    }
}
