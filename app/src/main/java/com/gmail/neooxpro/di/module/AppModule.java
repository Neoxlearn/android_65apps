package com.gmail.neooxpro.di.module;

import android.app.Application;
import android.content.Context;

import com.gmail.neooxpro.java.domain.interactor.ContactDetailsInterator;
import com.gmail.neooxpro.java.domain.interactor.ContactDetailsResolverRepository;
import com.gmail.neooxpro.java.domain.interactor.ContactListResolverRepository;
import com.gmail.neooxpro.java.domain.interactor.ContactsInteractor;

import com.gmail.neooxpro.java.domain.repo.IssueRepository;
import com.gmail.neooxpro.lib.service.ContactsResolver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application){
        this.application = application;
    }

    @Singleton
    @Provides
    public Application provideApplication(){
        return application;
    }

    @Singleton
    @Provides
    public Context provideContext(){
        return application;
    }

    @Singleton
    @Provides
    static IssueRepository provideRepository(ContactsResolver repository){
        return repository;
    }

}