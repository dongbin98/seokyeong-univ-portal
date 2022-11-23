package com.dbsh.skup.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.api.NoticeApi;
import com.dbsh.skup.model.NoticeData;
import com.dbsh.skup.client.NoticeClient;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeCenterViewModel extends ViewModel {
    public NoticeApi noticeApi;
    public MutableLiveData<ArrayList<NoticeData>> noticeDataLiveData = new MutableLiveData<>();
    public MutableLiveData<NoticeData> majorNoticeDataLiveData = new MutableLiveData<>();

    public void getNotice() {
        NoticeClient noticeClient = new NoticeClient();
        noticeApi = noticeClient.getNoticeApi();
        noticeApi.getNotice().enqueue(new Callback<Document>() {
            @Override
            public void onResponse(Call<Document> call, Response<Document> response) {
                if (response.isSuccessful()) {
                    Document document = response.body();
                    Elements noticeList = document.select(".bg1");
                    noticeList.addAll(document.select(".bg2"));

                    ArrayList<NoticeData> notices = new ArrayList<>();

                    for(Element e: noticeList) {
                        NoticeData noticeData = new NoticeData();
                        noticeData.setTitle(e.select(".title").text().substring(3));
                        noticeData.setDate(e.select(".date").text());
                        noticeData.setDepartment(e.select(".author").text());
                        noticeData.setType(e.select(".category").text());
                        noticeData.setNumber(Integer.parseInt(e.select(".num").text()));
                        noticeData.setUrl(e.select(".title").select("a").attr("href"));
                        notices.add(noticeData);
                    }
                    notices.sort(new Comparator<NoticeData>() {
                        @Override
                        public int compare(NoticeData noticeData, NoticeData t1) {
                            int result = 1;
                            if(noticeData.getNumber() >= t1.getNumber())
                                result = -1;
                            return result;
                        }
                    });
                    noticeDataLiveData.setValue(notices);
                }
            }

            @Override
            public void onFailure(Call<Document> call, Throwable t) {

            }
        });
    }

    public void getMajorNotice() {
        NoticeClient noticeClient = new NoticeClient();
        noticeApi = noticeClient.getNoticeApi();
        noticeApi.getNotice().enqueue(new Callback<Document>() {
            @Override
            public void onResponse(Call<Document> call, Response<Document> response) {
                if (response.isSuccessful()) {

                    /* 학과 공지사항의 경우
                    Elements noticeList = document.select(".bg1");
                        noticeList.addAll(document.select(".bg2"));

                        for (Element e : noticeList) {
                            NoticeData noticeData = new NoticeData();
                            noticeData.setTitle(e.select(".title").text().substring(3));
                            noticeData.setDate(e.select(".date").text());
                            noticeData.setDepartment(e.select(".author").text());
                            noticeData.setType(e.select(".category").text());
                            noticeData.setNumber(Integer.parseInt(e.select(".num").text()));
                            noticeData.setUrl(e.select(".title").select("a").attr("href"));
                            noticeDataArrayList.add(noticeData);
                        }
                     */

                    Document document = response.body();
                    Elements noticeList = document.select("tr.notice");

                    for(Element e: noticeList) {
                        NoticeData noticeData = new NoticeData();
                        noticeData.setTitle(e.select(".title").text().substring(3));
                        noticeData.setDate(e.select(".date").text());
                        noticeData.setDepartment(e.select(".author").text());
                        noticeData.setType(e.select(".category").text());
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
