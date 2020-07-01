package com.gmail.neooxpro.java.domain.model;

import com.gmail.neooxpro.java.domain.repo.CalendarRepository;

import java.util.Calendar;

import javax.inject.Inject;

public class CalendarModel implements CalendarRepository {
    private Calendar calendar;
    private boolean isChanged;

    @Inject
    public CalendarModel(){
        isChanged = false;
        calendar = Calendar.getInstance();
    }

    @Override
    public Calendar getMutableUserCalendar() {
        return isChanged ? calendar : Calendar.getInstance();
    }

    @Override
    public Calendar getNow() {
        return Calendar.getInstance();
    }

    @Override
    public void setYear(int year) {
        calendar.set(Calendar.YEAR, year);
        isCalendarChanged(true);
    }

    @Override
    public void setMonth(int month) {
        calendar.set(Calendar.MONTH, month - 1);
        isCalendarChanged(true);
    }

    @Override
    public void setDay(int day) {
        calendar.set(Calendar.DATE, day);
        isCalendarChanged(true);
    }

    @Override
    public void isCalendarChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }


}
