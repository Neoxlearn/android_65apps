package com.gmail.neooxpro.di.module;

import com.gmail.neooxpro.java.domain.interactor.ContactListResolverRepository;
import com.gmail.neooxpro.java.domain.interactor.ContactsInteractor;
import com.gmail.neooxpro.lib.di.scope.ContactsListScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactListModule {

    @ContactsListScope
    @Provides
    public ContactsInteractor provideContactInteractor(ContactListResolverRepository interactor) {
        return interactor;
    }
}
