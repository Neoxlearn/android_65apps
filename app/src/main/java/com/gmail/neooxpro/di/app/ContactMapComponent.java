package com.gmail.neooxpro.di.app;


import com.gmail.neooxpro.di.module.MapModule;
import com.gmail.neooxpro.di.module.ContactMapViewModelModule;
import com.gmail.neooxpro.lib.di.containers.ContactMapContainer;
import com.gmail.neooxpro.lib.di.scope.MapScope;

import dagger.Subcomponent;

@MapScope
@Subcomponent(modules = {ContactMapViewModelModule.class, MapModule.class})
public interface ContactMapComponent extends ContactMapContainer {

}
