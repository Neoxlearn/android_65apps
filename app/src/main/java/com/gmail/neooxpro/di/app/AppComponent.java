package com.gmail.neooxpro.di.app;


import com.gmail.neooxpro.app.AppDelegate;

import com.gmail.neooxpro.di.contact.ContactsDetailsComponent;
import com.gmail.neooxpro.di.contact.ContactsDetailsModule;
import com.gmail.neooxpro.di.contact.DetailsViewModelComponent;
import com.gmail.neooxpro.di.contacts.ContactsListComponent;
import com.gmail.neooxpro.di.contacts.ContactsListModule;
import com.gmail.neooxpro.di.contacts.ContactsViewModelComponent;
import com.gmail.neooxpro.di.module.AppModule;
import com.gmail.neooxpro.di.module.RepositoryModule;
import com.gmail.neooxpro.repo.IssueRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    DetailsViewModelComponent plusDetailsViewModelComponent();
    ContactsDetailsComponent plusContactsDetailsComponent(ContactsDetailsModule module);
    ContactsViewModelComponent plusContactsViewModelComponent();
    ContactsListComponent plusContactsListComponent(ContactsListModule module);
    void inject(AppDelegate appDelegate);

}
