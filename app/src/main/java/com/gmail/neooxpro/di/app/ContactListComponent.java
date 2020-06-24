package com.gmail.neooxpro.di.app;

import com.gmail.neooxpro.di.module.ContactListModule;
import com.gmail.neooxpro.lib.di.containers.ContactListContainer;
import com.gmail.neooxpro.di.module.ContactListViewModelModule;
import com.gmail.neooxpro.lib.di.scope.ContactsListScope;

import dagger.Subcomponent;

@ContactsListScope
@Subcomponent(modules = {ContactListViewModelModule.class, ContactListModule.class})
public interface ContactListComponent extends ContactListContainer {
}
