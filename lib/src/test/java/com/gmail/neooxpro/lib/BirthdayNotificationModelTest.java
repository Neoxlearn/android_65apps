package com.gmail.neooxpro.lib;


import com.gmail.neooxpro.java.domain.interactor.BirthdayNotificationInteractor;
import com.gmail.neooxpro.java.domain.interactor.BirthdayNotificationModel;
import com.gmail.neooxpro.java.domain.model.Contact;
import com.gmail.neooxpro.java.domain.repo.BirthdayNotificationRepository;
import com.gmail.neooxpro.lib.stub.BirthdayNotificationModelStub;
import com.gmail.neooxpro.lib.stub.BirthdayNotificationStub;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class BirthdayNotificationModelTest {
    private BirthdayNotificationRepository repository;
    private BirthdayNotificationInteractor interactor;

    @Before
    public void before(){
        repository = new BirthdayNotificationStub();
        interactor = new BirthdayNotificationModel(repository);
    }

    @Test
    public void WhenSetNotificationForContactThenGetSuccess(){
        Contact contact = new Contact("1", "Иван Иванович", null, null, "", "08-09");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1999);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DATE, 8);
        interactor.enableOrDisableBirthdayNotification(contact.getId(), contact.getName(), contact.getBirthday());
    }
}
