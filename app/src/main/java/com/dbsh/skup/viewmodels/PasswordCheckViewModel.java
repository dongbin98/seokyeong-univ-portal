package com.dbsh.skup.viewmodels;

import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.R;
import com.dbsh.skup.api.LoginApi;
import com.dbsh.skup.dto.RequestPasswordCheckData;
import com.dbsh.skup.dto.ResponsePassword;
import com.dbsh.skup.client.LoginClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordCheckViewModel extends ViewModel {

    public LoginApi loginApi;
    public MutableLiveData<String> checkState = new MutableLiveData<>();

    public EditText.OnFocusChangeListener editTextFocusListener() {
        return (v, hasFocus) -> {
            if (hasFocus)
                v.setBackgroundResource(R.drawable.edittext_white_focused_background);
            else
                v.setBackgroundResource(R.drawable.edittext_white_background);
        };
    }

    public void getPasswordCheckData(String id, String code) {
        LoginClient loginClient = new LoginClient();
        loginApi = loginClient.getLoginApi();
        loginApi.getPasswordCheck(new RequestPasswordCheckData(id, code, "sku")).enqueue(new Callback<ResponsePassword>() {
            @Override
            public void onResponse(Call<ResponsePassword> call, Response<ResponsePassword> response) {
                if (response.isSuccessful()) {
                    if (response.body().getRtnStatus().equals("S")) {
                        checkState.setValue("S");
                    } else {
                        checkState.setValue("F");
                    }
                } else {
                    checkState.setValue("F");
                }
            }

            @Override
            public void onFailure(Call<ResponsePassword> call, Throwable t) {
                checkState.setValue("N");
            }
        });
    }
}
