package com.gmail.neooxpro.di.app;

import com.gmail.neooxpro.di.module.ContactDetailsModule;
import com.gmail.neooxpro.lib.di.containers.ContactDetailsContainer;
import com.gmail.neooxpro.di.module.ContactDetailsViewModelModule;
import com.gmail.neooxpro.lib.di.scope.ContactsDetailsScope;

import dagger.Subcomponent;

@ContactsDetailsScope
@Subcomponent(modules = {ContactDetailsViewModelModule.class, ContactDetailsModule.class})
public interface ContactDetailsComponent extends ContactDetailsContainer {

}
