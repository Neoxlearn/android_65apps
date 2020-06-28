package com.gmail.neooxpro.lib.stub;

import com.gmail.neooxpro.java.domain.repo.BirthdayNotificationRepository;

import java.util.Calendar;

public class BirthdayNotificationStub implements BirthdayNotificationRepository {


    @Override
    public boolean checkAlarm(String id) {
        return false;
    }

    @Override
    public void makeAlarm(String id, String contactName, Calendar birthday) {

    }

    @Override
    public void closeAlarm(String id) {

    }
}
