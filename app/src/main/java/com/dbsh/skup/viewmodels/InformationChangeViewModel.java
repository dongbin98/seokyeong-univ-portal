package com.dbsh.skup.viewmodels;

import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.R;
import com.dbsh.skup.api.PortalApi;
import com.dbsh.skup.model.RequestInformationData;
import com.dbsh.skup.model.RequestInformationParameterData;
import com.dbsh.skup.model.ResponseInformation;
import com.dbsh.skup.model.ResponseInformationMap;
import com.dbsh.skup.repository.PortalRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationChangeViewModel extends ViewModel {

	public MutableLiveData<ResponseInformationMap> responseInformationMapLiveData = new MutableLiveData<>();
	public PortalApi portalApi;

    public EditText.OnFocusChangeListener editTextFocusListener() {
        return (v, hasFocus) -> {
            if (hasFocus)
                v.setBackgroundResource(R.drawable.edittext_white_focused_background);
            else
                v.setBackgroundResource(R.drawable.edittext_white_background);
        };
    }

	public void getInformation(String token, String id) {
		PortalRepository portalRepository = PortalRepository.getInstance(token);
		portalApi = PortalRepository.getPortalApi();
		RequestInformationParameterData parameter = new RequestInformationParameterData("1", id, id);
		portalApi.getInformation(new RequestInformationData(
				"education.cmn.CMN_01008_T.SELECT",
				"AL",
				token,
				"common/selectOne",
				"CMN_01008_T",
				id,
				parameter
		)).enqueue(new Callback<ResponseInformation>() {
			@Override
			public void onResponse(Call<ResponseInformation> call, Response<ResponseInformation> response) {
				if(response.isSuccessful()) {
					if(response.body().getRtnStatus().equals("S")) {
						responseInformationMapLiveData.setValue(response.body().getResponseLecturePlanSummaryMap());
					}
				}
			}

			@Override
			public void onFailure(Call<ResponseInformation> call, Throwable t) {

			}
		});
	}
}
