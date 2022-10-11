package com.dbsh.skup.tmpstructure.api;

import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import retrofit2.Retrofit;

public class RetrofitBusClient {
    private static RetrofitBusClient instance = null;
    private static RetrofitBusAPI retrofitBusAPI;

    private final static String BASE_URL = "http://ws.bus.go.kr/api/rest/";

    private RetrofitBusClient() {
        TikXml tikXml = new TikXml.Builder().exceptionOnUnreadXml(false).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(tikXml))
                .build();
        retrofitBusAPI = retrofit.create(RetrofitBusAPI.class);
    }

    /* Singleton Pattern */
    public static RetrofitBusClient getInstance() {
        if(instance == null) {
            instance = new RetrofitBusClient();
        }
        return instance;
    }

    public static RetrofitBusAPI getRetrofitBusAPI() {
        return retrofitBusAPI;
    }
}
