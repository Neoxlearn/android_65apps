package com.gmail.neooxpro.lib.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gmail.neooxpro.java.domain.repo.BirthdayNotificationRepository;
import com.gmail.neooxpro.lib.R;

import java.util.Calendar;

import javax.inject.Inject;

public class BirthdayNotification implements BirthdayNotificationRepository {

    private final Context context;
    private final Intent intent;
    private static final String ALARM_ACTION = "com.gmail.neooxpro.alarm";
    private final AlarmManager alarmMgr;

    @Inject
    public BirthdayNotification(@NonNull Context context) {
        this.context = context;
        intent = new Intent(ALARM_ACTION);

        intent.setClass(context, NotificationReceiver.class);
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public boolean checkAlarm(@NonNull String id) {

        return PendingIntent.getBroadcast(context.getApplicationContext(), id.hashCode(), intent,
                PendingIntent.FLAG_NO_CREATE) == null;
    }

    @Override
    public void makeAlarm(@NonNull String id,
                          @Nullable String contactName,
                          @NonNull Calendar birthday) {
        intent.putExtra("id", id);
        intent.putExtra("message", String.format(context.getResources()
                .getString(R.string.birthdayToday), contactName));
        intent.putExtra("name", contactName);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id.hashCode(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, birthday.getTimeInMillis(), alarmIntent);
    }

    @Override
    public void closeAlarm(@NonNull String id) {
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id.hashCode(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.cancel(alarmIntent);
        alarmIntent.cancel();
    }
}
