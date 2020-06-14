package com.gmail.neooxpro.di.contact;

import com.gmail.neooxpro.repo.IssueRepository;
import com.gmail.neooxpro.service.ContactsResolver;


import dagger.Module;
import dagger.Provides;

@Module
public final class DetailsViewModelModule {

    @Provides
    public IssueRepository getRepository() {
        return new ContactsResolver();
    }
}
