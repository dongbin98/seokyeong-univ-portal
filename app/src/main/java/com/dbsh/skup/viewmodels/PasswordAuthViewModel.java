package com.dbsh.skup.viewmodels;

import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.R;
import com.dbsh.skup.api.LoginApi;
import com.dbsh.skup.dto.RequestPasswordAuthData;
import com.dbsh.skup.dto.ResponsePassword;
import com.dbsh.skup.client.LoginClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordAuthViewModel extends ViewModel {

    public LoginApi loginApi;
    public MutableLiveData<String> authState = new MutableLiveData<>();

    public EditText.OnFocusChangeListener editTextFocusListener() {
        return (v, hasFocus) -> {
            if (hasFocus)
                v.setBackgroundResource(R.drawable.edittext_white_focused_background);
            else
                v.setBackgroundResource(R.drawable.edittext_white_background);
        };
    }

    public void getPasswordAuthData(String id, String name, String birth, String type) {
        LoginClient loginClient = new LoginClient();
        loginApi = loginClient.getLoginApi();
        loginApi.getPasswordAuth(new RequestPasswordAuthData(id, name, birth, type, "sku")).enqueue(new Callback<ResponsePassword>() {
            @Override
            public void onResponse(Call<ResponsePassword> call, Response<ResponsePassword> response) {
                if (response.isSuccessful()) {
                    if (response.body().getRtnStatus().equals("S")) {
                        authState.setValue("S");
                    } else {
                        authState.setValue("F");
                    }
                } else {
                    authState.setValue("F");
                }
            }

            @Override
            public void onFailure(Call<ResponsePassword> call, Throwable t) {
                authState.setValue("N");
            }
        });
    }
}
