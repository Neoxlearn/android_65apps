package com.gmail.neooxpro.java.domain.model;

import com.gmail.neooxpro.java.domain.repo.CalendarRepository;

import java.util.Calendar;

import javax.inject.Inject;

public class CalendarModel implements CalendarRepository {
    private Calendar calendar;

    @Inject
    public CalendarModel(){
        calendar = Calendar.getInstance();
    }

    @Override
    public Calendar getNow() {
        return calendar;
    }

    @Override
    public void setYear(int year) {
        calendar.set(Calendar.YEAR, year);
    }

    @Override
    public void setMonth(int month) {
        calendar.set(Calendar.MONTH, month - 1);
    }

    @Override
    public void setDay(int day) {
        calendar.set(Calendar.DATE, day);
    }



}
