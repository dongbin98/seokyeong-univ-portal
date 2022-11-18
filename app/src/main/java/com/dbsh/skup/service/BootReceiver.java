package com.dbsh.skup.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            SharedPreferences notification = context.getSharedPreferences("notice", Activity.MODE_PRIVATE);
            SharedPreferences.Editor notificationEdit = notification.edit();
            boolean notificationCheck = notification.getBoolean("checked", false);

            if(notificationCheck) {
                Intent noticeService = new Intent(context, NoticeNotificationService.class);
                noticeService.setAction("start");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(noticeService);
                } else {
                    context.startService(noticeService);
                }
            }
        }
    }
}
