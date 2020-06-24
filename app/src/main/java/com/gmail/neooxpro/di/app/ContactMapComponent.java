package com.gmail.neooxpro.di.app;


import com.gmail.neooxpro.di.module.ContactMapViewModelModule;
import com.gmail.neooxpro.lib.di.containers.ContactMapContainer;
import com.gmail.neooxpro.lib.di.scope.ContactMapScope;

import dagger.Subcomponent;

@ContactMapScope
@Subcomponent(modules = {ContactMapViewModelModule.class})
public interface ContactMapComponent extends ContactMapContainer {

}
