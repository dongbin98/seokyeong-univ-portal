package com.dbsh.skup.viewmodels;

import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.R;
import com.dbsh.skup.Service.PortalService;
import com.dbsh.skup.api.PortalApi;
import com.dbsh.skup.model.RequestLectureplanData;
import com.dbsh.skup.model.RequestLectureplanParameterData;
import com.dbsh.skup.model.ResponseLectureplan;
import com.dbsh.skup.model.ResponseLectureplanList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LecturePlanViewModel extends ViewModel {

    public MutableLiveData<ResponseLectureplanList>  responseLectureplanListLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> totalSizeLiveData = new MutableLiveData<>();

    public PortalApi portalApi;

    public void getLecturePlan(String token, String id, String year, String term) {
        PortalService portalService = PortalService.getInstance(token);
        portalApi = PortalService.getPortalApi();
        RequestLectureplanParameterData parameter = new RequestLectureplanParameterData(year, term, "%", "%", "%", "%", "1", "");
        portalApi.getLectureplanData(new RequestLectureplanData(
                "education.ucs.UCS_03100_T.SELECT",
                "AL",
                token,
                "common/selectList",
                "UCS_03090_T",
                id,
                parameter
        )).enqueue(new Callback<ResponseLectureplan>() {
            @Override
            public void onResponse(Call<ResponseLectureplan> call, Response<ResponseLectureplan> response) {
                if (response.isSuccessful()) {
                    // 졸업생의 경우 response.body() == null
                    // System.out.println(response.body());
                    if (response.body().getRtnStatus().equals("S")) {
                        ArrayList<ResponseLectureplanList> responseScholarListArrayList = response.body().getResponseLectureplanLists();
                        totalSizeLiveData.setValue(responseScholarListArrayList.size());
                        for (ResponseLectureplanList responseScholarList : responseScholarListArrayList) {
                            responseLectureplanListLiveData.setValue(responseScholarList);
                        }
                    } else {    // 잘못된 파라미터 참조 case
                        totalSizeLiveData.setValue(0);
                        responseLectureplanListLiveData.setValue(null);
                    }
                } else {        // null case
                    totalSizeLiveData.setValue(0);
                    responseLectureplanListLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseLectureplan> call, Throwable t) {

            }
        });
    }

    public EditText.OnFocusChangeListener searchFocusListener() {
        return (v, hasFocus) -> {
            if (hasFocus)
                v.setBackgroundResource(R.drawable.edittext_white_focused_background);
            else
                v.setBackgroundResource(R.drawable.edittext_white_background);
        };
    }
}
