package com.gmail.neooxpro.di.module;

import com.gmail.neooxpro.repo.IssueRepository;
import com.gmail.neooxpro.service.ContactsResolver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static IssueRepository provideRepository(){
        return new ContactsResolver();
    }
}