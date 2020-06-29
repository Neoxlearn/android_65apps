package com.gmail.neooxpro.java.domain.repo;

import java.util.Calendar;

public interface CalendarRepository {
    Calendar getNow();

    void setYear(int year);

    void setMonth(int month);

    void setDay(int day);



}
