package com.gmail.neooxpro.di.app;

import com.gmail.neooxpro.di.module.ContactListMapViewModelModule;
import com.gmail.neooxpro.di.module.MapModule;
import com.gmail.neooxpro.lib.di.containers.ContactListMapContainer;
import com.gmail.neooxpro.lib.di.scope.MapScope;

import dagger.Subcomponent;

@MapScope
@Subcomponent(modules = {ContactListMapViewModelModule.class, MapModule.class})
public interface ContactListMapComponent extends ContactListMapContainer {
}
