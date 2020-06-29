package com.gmail.neooxpro.lib.stub;

import android.app.AlarmManager;
import android.app.PendingIntent;


import com.gmail.neooxpro.java.domain.repo.BirthdayNotificationRepository;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BirthdayNotificationStub implements BirthdayNotificationRepository {
    private AlarmManager alarmMgr;
    private final PendingIntent PENDING_INTENT;
    private Map<String, Calendar> alarms = new HashMap<>();

    public BirthdayNotificationStub(AlarmManager alarmMgr, PendingIntent pendingIntent) {
        this.alarmMgr = alarmMgr;
        PENDING_INTENT = pendingIntent;
    }

    @Override
    public boolean checkAlarm(String id) {
        return !alarms.containsKey(id);
    }

    @Override
    public void makeAlarm(String id, String contactName, Calendar birthday) {
        alarms.put(id, birthday);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, birthday.getTimeInMillis(), PENDING_INTENT);
    }

    @Override
    public void closeAlarm(String id) {
        alarms.remove(id);
        alarmMgr.cancel(PENDING_INTENT);
    }


}
