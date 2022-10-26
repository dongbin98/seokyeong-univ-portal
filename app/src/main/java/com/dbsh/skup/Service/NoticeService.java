package com.dbsh.skup.Service;

import com.dbsh.skup.api.NoticeApi;
import com.github.slashrootv200.retrofithtmlconverter.HtmlConverterFactory;

import retrofit2.Retrofit;

public class NoticeService {
	private NoticeApi noticeApi;
	private Retrofit retrofit;

	public static String BASE_URL = "https://www.skuniv.ac.kr/";

	public NoticeService() {
		retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(HtmlConverterFactory.create(BASE_URL))
				.build();
		noticeApi = retrofit.create(NoticeApi.class);
	}

	public NoticeApi getNoticeApi() { return noticeApi; }
}
