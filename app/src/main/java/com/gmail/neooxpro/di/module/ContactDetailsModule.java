package com.gmail.neooxpro.di.module;

import com.gmail.neooxpro.java.domain.interactor.BirthdayNotificationInteractor;
import com.gmail.neooxpro.java.domain.interactor.BirthdayNotificationModel;
import com.gmail.neooxpro.java.domain.interactor.ContactDetailsInterator;
import com.gmail.neooxpro.java.domain.interactor.ContactDetailsModel;
import com.gmail.neooxpro.lib.di.scope.ContactsDetailsScope;


import dagger.Module;
import dagger.Provides;

@Module
public class ContactDetailsModule {

    @ContactsDetailsScope
    @Provides
    public ContactDetailsInterator provideContactDetailsInteractor(ContactDetailsModel interactor) {
        return interactor;
    }

    @ContactsDetailsScope
    @Provides
    public BirthdayNotificationInteractor provideBirthdayNotificationInteractor(BirthdayNotificationModel interactor) {
        return interactor;
    }


}
