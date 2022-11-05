package com.dbsh.skup.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.dbsh.skup.repository.NoticeRepository;
import com.dbsh.skup.views.MainActivity;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeNotificationService extends LifecycleService {
    private NotificationManager notificationManager;
    private Notification notification;

    private ServiceThread thread;

    private NoticeApi noticeApi;
    private MajorNoticeApi majorNoticeApi;

    private ArrayList<NoticeData> majorNoticeDataArrayList;

    public MutableLiveData<ArrayList<NoticeData>> noticeDataLiveData = new MutableLiveData<>();

    public static final String CHANNEL_ID = "NoticeServiceChannel";

    SharedPreferences notice;
    private int notificationId = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        noticeDataLiveData.observe(this, new Observer<ArrayList<NoticeData>>() {
            @Override
            public void onChanged(ArrayList<NoticeData> noticeData) {

                notice = getSharedPreferences("notice", Activity.MODE_PRIVATE);
                SharedPreferences.Editor currentNotice = notice.edit();
                int savedNoticeNumber = Integer.parseInt(notice.getString("noticeNumber", "0"));
                int i = 0;

                for (NoticeData notice : noticeData) {
                    String url = notice.getUrl();
                    int startIndex = url.indexOf("srl");

                    if(savedNoticeNumber < Integer.parseInt(url.substring(startIndex+4))) {
                        if(i == 0) {
                            // 가장 최근 공지 저장하기
                            currentNotice.putString("noticeUrl", url);
                            currentNotice.putString("noticeNumber", url.substring(startIndex+4));
                            currentNotice.apply();
                        }
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        createNotificationChannel();
                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
						PendingIntent pendingIntent;
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
							pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
						} else {
							pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
						}
                        notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                .setContentTitle(notice.getTitle())
                                .setSmallIcon(R.mipmap.ic_skup_logo)
                                .setDefaults(Notification.DEFAULT_SOUND)
                                .setOnlyAlertOnce(true)
                                .setAutoCancel(true)
                                .build();
                        notificationManager.notify(notificationId++, notification);
                    }
                    i++;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.stopForever();
        thread = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        majorNoticeDataArrayList = new ArrayList<>();
        myServiceHandler handler = new myServiceHandler();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle("새 공지사항 알리미 작동중")
                .setSmallIcon(R.mipmap.ic_skup_logo)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .build();

        startForeground(999, notification);
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            getNotice();
        }
    }

    public void createNotificationChannel() {
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

    public static void cancelNotification(Context context, int notifyId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notifyId);
    }

    public void clearNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public void getNotice() {
        NoticeRepository noticeRepository = new NoticeRepository();
        noticeApi = noticeRepository.getNoticeApi();
        ArrayList<NoticeData> noticeDataArrayList = new ArrayList<>();
        noticeApi.getNotice().enqueue(new Callback<Document>() {
            @Override
            public void onResponse(Call<Document> call, Response<Document> response) {
                if (response.isSuccessful()) {
                    Document document = response.body();
                    if(document != null) {
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
                        noticeDataLiveData.setValue(noticeDataArrayList);
                    }
                }
            }

            @Override
            public void onFailure(Call<Document> call, Throwable t) {

            }
        });
    }
}