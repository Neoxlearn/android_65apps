package com.gmail.neooxpro.di.contacts;

import com.gmail.neooxpro.di.scope.ContactsListScope;
import com.gmail.neooxpro.repo.IssueRepository;
import com.gmail.neooxpro.service.ContactsResolver;


import dagger.Module;
import dagger.Provides;

@Module
public class ContactsViewModelModule {
    @Provides
    @ContactsListScope
    public IssueRepository getRepository() {
        return new ContactsResolver();
    }
}
