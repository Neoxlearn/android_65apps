package com.gmail.neooxpro.java.domain.interactor;


import java.util.Calendar;

public interface BirthdayNotificationInteractor {
    void enableOrDisableBirthdayNotification(String id, String contactName, Calendar birthday);
}
