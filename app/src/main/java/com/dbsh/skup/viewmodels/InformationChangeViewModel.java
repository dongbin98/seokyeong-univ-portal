package com.dbsh.skup.viewmodels;

import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.R;
import com.dbsh.skup.api.PortalApi;
import com.dbsh.skup.dto.RequestInformationChangeData;
import com.dbsh.skup.dto.RequestInformationChangeEnglishNameData;
import com.dbsh.skup.dto.RequestInformationChangeEnglishNameParameterData;
import com.dbsh.skup.dto.RequestInformationChangeParameterData;
import com.dbsh.skup.dto.RequestInformationData;
import com.dbsh.skup.dto.RequestInformationParameterData;
import com.dbsh.skup.dto.ResponseInformation;
import com.dbsh.skup.dto.ResponseInformationChange;
import com.dbsh.skup.dto.ResponseInformationMap;
import com.dbsh.skup.client.PortalClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationChangeViewModel extends ViewModel {

	public MutableLiveData<ResponseInformationMap> responseInformationMapLiveData = new MutableLiveData<>();
	public MutableLiveData<String> changeInformationSuccess = new MutableLiveData<>();
	public MutableLiveData<String> changeEnglishNameSuccess = new MutableLiveData<>();
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
		PortalClient portalClient = PortalClient.getInstance(token);
		portalApi = PortalClient.getPortalApi();
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

	public void changeInformation(String token, String id, String tel, String phone, String guardianPhone, String address1, String address2, String zipcode, String gunmulNo, String email, String ip) {
		PortalClient portalClient = PortalClient.getInstance(token);
		portalApi = PortalClient.getPortalApi();

		String[] tels = tel.split("-");
		String[] phones = phone.split("-");
		String[] guradianPhones = guardianPhone.split("-");

		RequestInformationChangeParameterData parameter = new RequestInformationChangeParameterData(
				address1,
				address2,
				email,
				gunmulNo,
				guradianPhones[0],
				guradianPhones[1],
				guradianPhones[2],
				phones[0],
				phones[1],
				phones[2],
				id,
				address1,
				address2,
				zipcode,
				zipcode.substring(0, 3),
				zipcode.substring(2, 5),
				id,
				tels[0],
				tels[1],
				tels[2],
				ip,
				id,
				"CMN_01008_T",
				zipcode.substring(0, 3),
				zipcode.substring(2, 5)
		);
		portalApi.changeInformation(new RequestInformationChangeData(
				"education.cmn.CMN_01008_T.UPDATE_USR_PERSONAL_MAT_INFO",
				"AL",
				token,
				"common/singleProcessing",
				"CMN_01008_T",
				id,
				parameter
		)).enqueue(new Callback<ResponseInformationChange>() {
			@Override
			public void onResponse(Call<ResponseInformationChange> call, Response<ResponseInformationChange> response) {
				if(response.isSuccessful()) {
					if(response.body().getRtnStatus().equals("S")) {
						changeInformationSuccess.setValue("S");
					} else {
						changeInformationSuccess.setValue("F");
					}
				} else {
					changeInformationSuccess.setValue("F");
				}
			}

			@Override
			public void onFailure(Call<ResponseInformationChange> call, Throwable t) {
				changeInformationSuccess.setValue("N");
			}
		});
	}

	public void changeEnglishName(String token, String id, String ip, String name) {
		PortalClient portalClient = PortalClient.getInstance(token);
		portalApi = PortalClient.getPortalApi();

		RequestInformationChangeEnglishNameParameterData parameter = new RequestInformationChangeEnglishNameParameterData(
				name,
				id,
				"CMN_01008_T",
				ip,
				id,
				id
		);
		portalApi.changeEnglishName(new RequestInformationChangeEnglishNameData(
				"education.cmn.CMN_01008_T.UPDATE_USR_MASTER",
				"AL",
				token,
				"common/singleProcessing",
				"CMN_01008_T",
				id,
				parameter
		)).enqueue(new Callback<ResponseInformationChange>() {
			@Override
			public void onResponse(Call<ResponseInformationChange> call, Response<ResponseInformationChange> response) {
				if(response.isSuccessful()) {
					if(response.body().getRtnStatus().equals("S")) {
						changeEnglishNameSuccess.setValue("S");
					} else {
						changeEnglishNameSuccess.setValue("F");
					}
				} else {
					changeEnglishNameSuccess.setValue("F");
				}
			}

			@Override
			public void onFailure(Call<ResponseInformationChange> call, Throwable t) {
				changeEnglishNameSuccess.setValue("N");
			}
		});
	}
}
