package com.gmail.neooxpro.java.domain.repo;

import java.util.Calendar;

public interface CalendarRepository {
    Calendar getMutableUserCalendar();

    Calendar getNow();

    void setYear(int year);

    void setMonth(int month);

    void setDay(int day);

    void isCalendarChanged(boolean isChanged);

}
