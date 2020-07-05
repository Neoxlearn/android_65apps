package com.gmail.neooxpro.di.module;

import android.app.Application;
import android.content.Context;

import com.gmail.neooxpro.java.domain.model.CalendarModel;
import com.gmail.neooxpro.java.domain.repo.BirthdayNotificationRepository;
import com.gmail.neooxpro.java.domain.repo.CalendarRepository;
import com.gmail.neooxpro.java.domain.repo.IssueRepository;
import com.gmail.neooxpro.lib.service.BirthdayNotification;
import com.gmail.neooxpro.lib.service.ContactsResolver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return application;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return application;
    }

    @Singleton
    @Provides
    static IssueRepository provideRepository(ContactsResolver repository) {
        return repository;
    }

    @Singleton
    @Provides
    static BirthdayNotificationRepository provideBirthdayNotificationRepository(BirthdayNotification repository) {
        return repository;
    }

    @Singleton
    @Provides
    static CalendarRepository provideCalendarRepository(CalendarModel calendarModel) {
        return calendarModel;
    }

}
