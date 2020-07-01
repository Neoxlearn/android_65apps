package com.gmail.neooxpro.lib;


import android.app.AlarmManager;
import android.app.PendingIntent;

import com.gmail.neooxpro.java.domain.interactor.BirthdayNotificationInteractor;
import com.gmail.neooxpro.java.domain.interactor.BirthdayNotificationModel;
import com.gmail.neooxpro.java.domain.model.CalendarModel;
import com.gmail.neooxpro.java.domain.model.Contact;
import com.gmail.neooxpro.java.domain.repo.BirthdayNotificationRepository;
import com.gmail.neooxpro.java.domain.repo.CalendarRepository;
import com.gmail.neooxpro.lib.stub.BirthdayNotificationStub;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;

import static org.mockito.Mockito.verify;

public class BirthdayNotificationModelTest {

    private BirthdayNotificationRepository repository;

    @Mock
    private AlarmManager alarmMgr;
    @Mock
    private PendingIntent PENDING_INTENT;
    private BirthdayNotificationInteractor interactor;
    private CalendarRepository calendarRepository;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        repository = new BirthdayNotificationStub(alarmMgr, PENDING_INTENT);

        calendarRepository = new CalendarModel();
        interactor = new BirthdayNotificationModel(repository, calendarRepository);

    }

    @Test
    public void When_Contact_Not_Have_Notification_And_Set_Notification_On_Next_Year_Then_Get_Success(){
        Contact contact = new Contact("101", "Иван Иванович", new ArrayList<>(), new ArrayList<>(), "", "--09-08");
        Calendar nextBirthday = Calendar.getInstance();
        nextBirthday.set(Calendar.YEAR, 2000);
        nextBirthday.set(Calendar.MONTH, Calendar.SEPTEMBER);
        nextBirthday.set(Calendar.DATE, 8);
        nextBirthday.set(Calendar.HOUR, 0);
        nextBirthday.set(Calendar.MINUTE, 0);
        nextBirthday.set(Calendar.SECOND, 0);
        calendarRepository.setYear(1999);
        calendarRepository.setMonth(9);
        calendarRepository.setDay(9);

        interactor.enableOrDisableBirthdayNotification(contact.getId(), contact.getName(), contact.getBirthday());
        verify(alarmMgr).set(AlarmManager.RTC_WAKEUP, nextBirthday.getTimeInMillis(), PENDING_INTENT);
    }

    @Test
    public void When_Contact_Not_Have_Notification_And_Set_Notification_On_Current_Year_Then_Get_Success(){
        Contact contact = new Contact("101", "Иван Иванович", new ArrayList<>(), new ArrayList<>(), "", "--09-08");
        Calendar nextBirthday = Calendar.getInstance();
        nextBirthday.set(Calendar.YEAR, 1999);
        nextBirthday.set(Calendar.MONTH, Calendar.SEPTEMBER);
        nextBirthday.set(Calendar.DATE, 8);
        nextBirthday.set(Calendar.HOUR, 0);
        nextBirthday.set(Calendar.MINUTE, 0);
        nextBirthday.set(Calendar.SECOND, 0);
        calendarRepository.setYear(1999);
        calendarRepository.setMonth(9);
        calendarRepository.setDay(7);
        interactor.enableOrDisableBirthdayNotification(contact.getId(), contact.getName(), contact.getBirthday());
        verify(alarmMgr).set(AlarmManager.RTC_WAKEUP, nextBirthday.getTimeInMillis(), PENDING_INTENT);
    }

    @Test
    public void When_Contact_Have_Notification_And_Remove_Notification_Then_Get_Success(){
        Contact contact = new Contact("102", "Павел Павлович", new ArrayList<>(), new ArrayList<>(), "", "--09-08");
        Calendar nextBirthday = Calendar.getInstance();
        nextBirthday.set(Calendar.YEAR, 2000);
        nextBirthday.set(Calendar.MONTH, Calendar.SEPTEMBER);
        nextBirthday.set(Calendar.DATE, 8);
        nextBirthday.set(Calendar.HOUR, 0);
        nextBirthday.set(Calendar.MINUTE, 0);
        nextBirthday.set(Calendar.SECOND, 0);
        calendarRepository.setYear(1999);
        calendarRepository.setMonth(9);
        calendarRepository.setDay(9);
        repository.makeAlarm(contact.getId(),contact.getName(), nextBirthday);
        interactor.enableOrDisableBirthdayNotification(contact.getId(), contact.getName(), contact.getBirthday());
        verify(alarmMgr).cancel(PENDING_INTENT);
    }

    @Test
    public void When_Contact_Not_Have_Notification_And_Have_Birthday_On_29_Feb_Set_Notification_On_Next_Leap_Year_Then_Get_Success(){
        Contact contact = new Contact("102", "Павел Павлович", new ArrayList<>(), new ArrayList<>(), "", "--02-29");
        Calendar nextBirthday = Calendar.getInstance();
        nextBirthday.set(Calendar.YEAR, 2000);
        nextBirthday.set(Calendar.MONTH, Calendar.FEBRUARY);
        nextBirthday.set(Calendar.DATE, 29);
        nextBirthday.set(Calendar.HOUR, 0);
        nextBirthday.set(Calendar.MINUTE, 0);
        nextBirthday.set(Calendar.SECOND, 0);
        calendarRepository.setYear(1999);
        calendarRepository.setMonth(3);
        calendarRepository.setDay(2);

        interactor.enableOrDisableBirthdayNotification(contact.getId(), contact.getName(), contact.getBirthday());
        verify(alarmMgr).set(AlarmManager.RTC_WAKEUP, nextBirthday.getTimeInMillis(), PENDING_INTENT);
    }

    @Test
    public void When_Contact_Not_Have_Notification_And_Have_Birthday_On_29_Feb_And_Current_Year_Is_Leap_Set_Notification_On_Next_Leap_Year_Then_Get_Success(){
        Contact contact = new Contact("102", "Иван Иванович", new ArrayList<>(), new ArrayList<>(), "", "--02-29");
        Calendar nextBirthday = Calendar.getInstance();
        nextBirthday.set(Calendar.YEAR, 2004);
        nextBirthday.set(Calendar.MONTH, Calendar.FEBRUARY);
        nextBirthday.set(Calendar.DATE, 29);
        nextBirthday.set(Calendar.HOUR, 0);
        nextBirthday.set(Calendar.MINUTE, 0);
        nextBirthday.set(Calendar.SECOND, 0);
        calendarRepository.setYear(2000);
        calendarRepository.setMonth(3);
        calendarRepository.setDay(1);

        interactor.enableOrDisableBirthdayNotification(contact.getId(), contact.getName(), contact.getBirthday());
        verify(alarmMgr).set(AlarmManager.RTC_WAKEUP, nextBirthday.getTimeInMillis(), PENDING_INTENT);
    }

}
