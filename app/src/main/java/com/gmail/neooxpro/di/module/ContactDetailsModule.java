package com.gmail.neooxpro.di.module;

import com.gmail.neooxpro.java.domain.interactor.ContactDetailsInterator;
import com.gmail.neooxpro.java.domain.interactor.ContactDetailsResolverRepository;
import com.gmail.neooxpro.lib.di.scope.ContactsDetailsScope;


import dagger.Module;
import dagger.Provides;

@Module
public class ContactDetailsModule {

    @ContactsDetailsScope
    @Provides
    public ContactDetailsInterator provideContactDetailsInteractor(ContactDetailsResolverRepository interactor) {
        return interactor;
    }
}
