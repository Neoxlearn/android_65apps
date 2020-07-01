package com.gmail.neooxpro.java.domain.repo;

import java.util.Calendar;

public interface BirthdayNotificationRepository {
    boolean checkAlarm(String id);

    void makeAlarm(String id, String contactName, Calendar birthday);

    void closeAlarm(String id);
}
