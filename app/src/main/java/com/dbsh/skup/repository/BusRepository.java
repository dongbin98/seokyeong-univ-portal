package com.dbsh.skup.repository;

import com.dbsh.skup.api.BusApi;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import retrofit2.Retrofit;

public class BusRepository {
    private static BusRepository instance = null;
    private static BusApi busApi;

    private final static String BASE_URL = "http://ws.bus.go.kr/api/rest/";

    private BusRepository() {
        TikXml tikXml = new TikXml.Builder().exceptionOnUnreadXml(false).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(tikXml))
                .build();
        busApi = retrofit.create(BusApi.class);
    }

    /* Singleton Pattern */
    public static BusRepository getInstance() {
        if(instance == null) {
            instance = new BusRepository();
        }
        return instance;
    }

    public static BusApi getBusApi() {
        return busApi;
    }
}
