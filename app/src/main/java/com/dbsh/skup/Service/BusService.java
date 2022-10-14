package com.dbsh.skup.Service;

import com.dbsh.skup.api.BusApi;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import retrofit2.Retrofit;

public class BusService {
    private static BusService instance = null;
    private static BusApi busApi;

    private final static String BASE_URL = "http://ws.bus.go.kr/api/rest/";

    private BusService() {
        TikXml tikXml = new TikXml.Builder().exceptionOnUnreadXml(false).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(tikXml))
                .build();
        busApi = retrofit.create(BusApi.class);
    }

    /* Singleton Pattern */
    public static BusService getInstance() {
        if(instance == null) {
            instance = new BusService();
        }
        return instance;
    }

    public static BusApi getBusApi() {
        return busApi;
    }
}
