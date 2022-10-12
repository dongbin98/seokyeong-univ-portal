package com.dbsh.skup.tmpstructure.Service;

import com.dbsh.skup.tmpstructure.api.PortalApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PortalService {
	private static PortalService instance = null;
	private static PortalApi portalApi;

	private final static String BASE_URL = "https://sportal.skuniv.ac.kr/sportal/";

	private PortalService() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		portalApi = retrofit.create(PortalApi.class);
	}

	public static PortalService getInstance() {
		if(instance == null) {
			instance = new PortalService();
		}
		return instance;
	}

	public static PortalApi getPortalApi() {
		return portalApi;
	}
}
