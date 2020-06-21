package com.gmail.neooxpro.di.module;

import android.app.Application;
import android.content.Context;

import com.gmail.neooxpro.java.domain.interactor.ContactsInteractorImpl;
import com.gmail.neooxpro.java.domain.interactor.ContactsInteractor;
import com.gmail.neooxpro.java.domain.repo.IssueRepository;
import com.gmail.neooxpro.lib.service.ContactsResolver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static IssueRepository provideRepository(ContactsResolver repository){
        return repository;
    }

    @Singleton
    @Provides
    public Context provideContext(Application application){
        return application;
    }

    @Singleton
    @Provides
    public ContactsInteractor provideContactInteractor(ContactsInteractorImpl interactor) {
        return interactor;
    }


}