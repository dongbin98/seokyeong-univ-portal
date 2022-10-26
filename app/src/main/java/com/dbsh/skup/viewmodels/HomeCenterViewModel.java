package com.dbsh.skup.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.Service.MajorNoticeService;
import com.dbsh.skup.Service.NoticeService;
import com.dbsh.skup.api.MajorNoticeApi;
import com.dbsh.skup.api.NoticeApi;
import com.dbsh.skup.data.NoticeData;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeCenterViewModel extends ViewModel {
    public NoticeApi noticeApi;
    public MajorNoticeApi majorNoticeApi;
    public MutableLiveData<NoticeData> noticeDataLiveData = new MutableLiveData<>();
    public MutableLiveData<NoticeData> majorNoticeDataLiveData = new MutableLiveData<>();

    public void getNotice() {
        NoticeService noticeService = new NoticeService();
        noticeApi = noticeService.getNoticeApi();
        noticeApi.getNotice().enqueue(new Callback<Document>() {
            @Override
            public void onResponse(Call<Document> call, Response<Document> response) {
                if (response.isSuccessful()) {
                    Document document = response.body();
                    Elements noticeList = document.select(".bg1");
                    noticeList.addAll(document.select(".bg2"));

                    for(Element e: noticeList) {
                        NoticeData noticeData = new NoticeData();
                        noticeData.setTitle(e.select(".title").text().substring(3));
                        noticeData.setDate(e.select(".date").text());
                        noticeData.setDepartment(e.select(".author").text());
                        noticeData.setType(e.select(".category").text());
                        noticeData.setNumber(Integer.parseInt(e.select(".num").text()));
                        noticeData.setUrl(e.select(".title").select("a").attr("href"));
                        noticeDataLiveData.setValue(noticeData);
                    }
                }
            }

            @Override
            public void onFailure(Call<Document> call, Throwable t) {

            }
        });
    }

    public void getMajorNotice() {
        MajorNoticeService majorNoticeService = new MajorNoticeService();
        majorNoticeApi = majorNoticeService.getNoticeApi();
        majorNoticeApi.getMajorNotice().enqueue(new Callback<Document>() {
            @Override
            public void onResponse(Call<Document> call, Response<Document> response) {
                if (response.isSuccessful()) {
                    Document document = response.body();
                    Elements noticeList = document.select("tr.notice");

                    for(Element e: noticeList) {
                        NoticeData noticeData = new NoticeData();
                        noticeData.setTitle(e.select(".title").text());
                        noticeData.setDate(e.select(".date").text());
                        noticeData.setDepartment(e.select(".author").text());
                        noticeData.setType(e.select("td.notice").text());
                        noticeData.setUrl(e.select(".title").select("a").attr("href"));
                        majorNoticeDataLiveData.setValue(noticeData);
                    }
                }
            }

            @Override
            public void onFailure(Call<Document> call, Throwable t) {

            }
        });
    }
}
