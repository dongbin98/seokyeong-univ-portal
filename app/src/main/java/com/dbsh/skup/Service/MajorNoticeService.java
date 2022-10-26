package com.dbsh.skup.Service;

import com.dbsh.skup.api.MajorNoticeApi;
import com.github.slashrootv200.retrofithtmlconverter.HtmlConverterFactory;

import retrofit2.Retrofit;

public class MajorNoticeService {
	private MajorNoticeApi majorNoticeApi;
	private Retrofit retrofit;

	public static String BASE_URL = "https://ce.skuniv.ac.kr/";

	public MajorNoticeService() {
		retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(HtmlConverterFactory.create(BASE_URL))
				.build();
		majorNoticeApi = retrofit.create(MajorNoticeApi.class);
	}

	public MajorNoticeApi getNoticeApi() { return majorNoticeApi; }
}
