package com.dbsh.skup.api;

import org.jsoup.nodes.Document;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NoticeApi {
	/* Notice */
	@GET("notice")
	Call<Document> getNotice();
}
