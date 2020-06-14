package com.gmail.neooxpro.di.contact;

import com.gmail.neooxpro.di.scope.ContactsDetailsScope;
import com.gmail.neooxpro.repo.IssueRepository;
import com.gmail.neooxpro.service.ContactsResolver;


import dagger.Module;
import dagger.Provides;

@Module
public final class DetailsViewModelModule {

    @Provides
    @ContactsDetailsScope
    public IssueRepository getRepository() {
        return new ContactsResolver();
    }
}
