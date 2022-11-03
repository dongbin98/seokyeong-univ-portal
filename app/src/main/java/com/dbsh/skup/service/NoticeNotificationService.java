package com.dbsh.skup.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.dbsh.skup.R;
import com.dbsh.skup.api.MajorNoticeApi;
import com.dbsh.skup.api.NoticeApi;
import com.dbsh.skup.data.NoticeData;
import com.dbsh.skup.repository.MajorNoticeRepository;
import com.dbsh.skup.repository.NoticeRepository;
import com.dbsh.skup.views.MainActivity;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeNotificationService extends Service {
    private NotificationManager notificationManager;
    private Notification notification;
    private ServiceThread thread;
    private NoticeApi noticeApi;
    private MajorNoticeApi majorNoticeApi;
    private MutableLiveData<NoticeData> noticeDataLiveData = new MutableLiveData<>();
    private MutableLiveData<NoticeData> majorNoticeDataLiveData = new MutableLiveData<>();

    public static final String CHANNEL_ID = "NoticeServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        thread.stopForever();
        thread = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            createNotificationChannel();
            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

            notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentTitle("공지사항 알림")
                    .setTicker("알림")
                    .setSmallIcon(R.mipmap.ic_skup_logo)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setOnlyAlertOnce(true)
                    .setAutoCancel(true)
                    .build();

            startForeground(1, notification);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public void getNotice() {
        NoticeRepository noticeRepository = new NoticeRepository();
        noticeApi = noticeRepository.getNoticeApi();
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
        MajorNoticeRepository majorNoticeRepository = new MajorNoticeRepository();
        majorNoticeApi = majorNoticeRepository.getNoticeApi();
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