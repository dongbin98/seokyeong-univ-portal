package com.dbsh.skup.repository;

import com.dbsh.skup.api.NoticeApi;
import com.github.slashrootv200.retrofithtmlconverter.HtmlConverterFactory;

import retrofit2.Retrofit;

public class NoticeRepository {
	private NoticeApi noticeApi;
	private Retrofit retrofit;

	public static String BASE_URL = "https://www.skuniv.ac.kr/";

	public NoticeRepository() {
		retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(HtmlConverterFactory.create(BASE_URL))
				.build();
		noticeApi = retrofit.create(NoticeApi.class);
	}

	public NoticeApi getNoticeApi() { return noticeApi; }
}
