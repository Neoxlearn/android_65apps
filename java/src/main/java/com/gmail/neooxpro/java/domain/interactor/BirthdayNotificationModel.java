package com.gmail.neooxpro.java.domain.interactor;

import com.gmail.neooxpro.java.domain.model.CalendarModel;
import com.gmail.neooxpro.java.domain.repo.BirthdayNotificationRepository;
import com.gmail.neooxpro.java.domain.repo.CalendarRepository;

import java.util.Calendar;

import javax.inject.Inject;

public class BirthdayNotificationModel implements BirthdayNotificationInteractor{

    private final BirthdayNotificationRepository repository;
    private final CalendarRepository calendarRepository;

    @Inject
    public BirthdayNotificationModel(BirthdayNotificationRepository repository, CalendarRepository calendarRepository) {
        this.repository = repository;
        this.calendarRepository = calendarRepository;
    }

    @Override
    public void enableOrDisableBirthdayNotification(String id, String contactName, Calendar birthday) {
        if (checkAlarm(id)){
            makeNotification(id, contactName, birthday);
        } else {
            repository.closeAlarm(id);
        }

    }

    @Override
    public boolean checkAlarm(String id) {
        return repository.checkAlarm(id);
    }

    private void makeNotification(String id, String contactName, Calendar birthday){
        checkDate(birthday);
        repository.makeAlarm(id, contactName, birthday);
    }

    private void checkDate(Calendar birthday){
        Calendar calendar = calendarRepository.getMutableUserCalendar();
        int alarmYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        birthday.set(Calendar.HOUR, 0);
        birthday.set(Calendar.MINUTE, 0);
        birthday.set(Calendar.SECOND, 0);
        if (curMonth > birthday.get(Calendar.MONTH) || (curMonth == birthday.get(Calendar.MONTH) && curDay >= birthday.get(Calendar.DAY_OF_MONTH)))
        {
            alarmYear++;
        }
        if (birthday.get(Calendar.MONTH) == 1 && birthday.get(Calendar.DAY_OF_MONTH) == 29) {
            if (isNormalYear(alarmYear)) {
                alarmYear = (((alarmYear / 4) * 4) + 4);
            }
        }
        birthday.set(Calendar.YEAR, alarmYear);
    }

    private boolean isNormalYear(int year) {
        if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
            return false;
        } else {
            return true;
        }
    }
}
