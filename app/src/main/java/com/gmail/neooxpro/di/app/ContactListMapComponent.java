package com.gmail.neooxpro.di.app;

import com.gmail.neooxpro.di.module.ContactListMapModule;
import com.gmail.neooxpro.di.module.ContactListMapViewModelModule;
import com.gmail.neooxpro.lib.di.containers.ContactListMapContainer;
import com.gmail.neooxpro.lib.di.scope.ContactListMapScope;

import dagger.Subcomponent;

@ContactListMapScope
@Subcomponent(modules = {ContactListMapViewModelModule.class, ContactListMapModule.class})
public interface ContactListMapComponent extends ContactListMapContainer {
}
