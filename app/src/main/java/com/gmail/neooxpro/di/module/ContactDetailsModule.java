package com.gmail.neooxpro.di.module;

import com.gmail.neooxpro.java.domain.interactor.BirthdayNotificationInteractor;
import com.gmail.neooxpro.java.domain.interactor.BirthdayNotificationModel;
import com.gmail.neooxpro.java.domain.interactor.ContactDetailsInterator;
import com.gmail.neooxpro.java.domain.interactor.ContactDetailsModel;
import com.gmail.neooxpro.lib.di.scope.ContactsDetailsScope;


import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;

@Module
public class ContactDetailsModule {

    @ContactsDetailsScope
    @Provides
    @NonNull
    public ContactDetailsInterator provideContactDetailsInteractor(
            @NonNull ContactDetailsModel interactor) {
        return interactor;
    }

    @ContactsDetailsScope
    @Provides
    @NonNull
    public BirthdayNotificationInteractor provideBirthdayNotificationInteractor(
            @NonNull BirthdayNotificationModel interactor) {
        return interactor;
    }


}
