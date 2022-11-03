package com.dbsh.skup.repository;

import com.dbsh.skup.api.MajorNoticeApi;
import com.github.slashrootv200.retrofithtmlconverter.HtmlConverterFactory;

import retrofit2.Retrofit;

public class MajorNoticeRepository {
	private MajorNoticeApi majorNoticeApi;
	private Retrofit retrofit;

	public static String BASE_URL = "https://ce.skuniv.ac.kr/";

	public MajorNoticeRepository() {
		retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(HtmlConverterFactory.create(BASE_URL))
				.build();
		majorNoticeApi = retrofit.create(MajorNoticeApi.class);
	}

	public MajorNoticeApi getNoticeApi() { return majorNoticeApi; }
}
