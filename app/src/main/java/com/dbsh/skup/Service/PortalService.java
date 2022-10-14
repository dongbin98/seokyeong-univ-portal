package com.dbsh.skup.Service;

import com.dbsh.skup.api.PortalApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PortalService {
	private static PortalService instance = null;
	private static PortalApi portalApi;

	private final static String BASE_URL = "https://sportal.skuniv.ac.kr/sportal/";
	private static String accessToken;

	private PortalService(String accessToken) {
		this.accessToken = accessToken;
		Retrofit retrofit = new Retrofit.Builder()
				.client(client)
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		portalApi = retrofit.create(PortalApi.class);
	}

	public static PortalService getInstance(String token) {
		if(instance == null) {
			instance = new PortalService(token);
		} else {
			instance.setAccessToken(token);
		}
		return instance;
	}

	OkHttpClient client = new OkHttpClient.Builder()
			.addInterceptor(new Interceptor() {
				@Override
				public Response intercept(Chain chain) throws IOException {
					Request request = chain.request().newBuilder()
							.addHeader("Authorization", "Bearer " + accessToken).build();
					return chain.proceed(request);
				}
			}).build();

	public static String getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(String accessToken) {
		PortalService.accessToken = accessToken;
	}

	public static PortalApi getPortalApi() { return portalApi; }
}
