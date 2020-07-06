package com.gmail.neooxpro.java.domain.interactor;

import com.gmail.neooxpro.java.domain.repo.BirthdayNotificationRepository;
import com.gmail.neooxpro.java.domain.repo.CalendarRepository;

import java.util.Calendar;

import javax.inject.Inject;

public class BirthdayNotificationModel implements BirthdayNotificationInteractor {

    private final BirthdayNotificationRepository repository;
    private final CalendarRepository calendarRepository;
    private static final int LEAP_FEBRUARY = 29;
    private static final int INCREASE_ALARM_YEAR = 4;
    private static final int LEAR_YEAR_EVERY_FOUR_HUNDRED = 400;
    private static final int LEAR_YEAR_EVERY_ONE_HUNDRED = 100;
    private static final int LEAR_YEAR_EVERY_FOUR = 4;

    @Inject
    public BirthdayNotificationModel(BirthdayNotificationRepository repository, CalendarRepository calendarRepository) {
        this.repository = repository;
        this.calendarRepository = calendarRepository;
    }

    @Override
    public void enableOrDisableBirthdayNotification(String id, String contactName, Calendar birthday) {
        if (checkAlarm(id)) {
            makeNotification(id, contactName, birthday);
        } else {
            repository.closeAlarm(id);
        }

    }

    @Override
    public boolean checkAlarm(String id) {
        return repository.checkAlarm(id);
    }

    private void makeNotification(String id, String contactName, Calendar birthday) {
        checkDate(birthday);
        repository.makeAlarm(id, contactName, birthday);
    }

    private void checkDate(Calendar birthday) {
        Calendar calendar = calendarRepository.getMutableUserCalendar();
        int alarmYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        birthday.set(Calendar.HOUR, 0);
        birthday.set(Calendar.MINUTE, 0);
        birthday.set(Calendar.SECOND, 0);
        if (curMonth > birthday.get(Calendar.MONTH) || curMonth == birthday.get(Calendar.MONTH)
                && curDay >= birthday.get(Calendar.DAY_OF_MONTH)) {
            alarmYear++;
        }
        if (birthday.get(Calendar.MONTH) == Calendar.FEBRUARY
                && birthday.get(Calendar.DAY_OF_MONTH) == LEAP_FEBRUARY && isNormalYear(alarmYear)) {
                alarmYear = (alarmYear / INCREASE_ALARM_YEAR) * INCREASE_ALARM_YEAR
                        + INCREASE_ALARM_YEAR;
        }
        birthday.set(Calendar.YEAR, alarmYear);
    }

    private boolean isNormalYear(int year) {
        return (year % LEAR_YEAR_EVERY_FOUR_HUNDRED != 0) && ((year % LEAR_YEAR_EVERY_FOUR != 0)
                || (year % LEAR_YEAR_EVERY_ONE_HUNDRED == 0));
    }
}
