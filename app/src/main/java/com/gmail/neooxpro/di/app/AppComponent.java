package com.gmail.neooxpro.di.app;


import com.gmail.neooxpro.lib.di.app.AppContainer;
import com.gmail.neooxpro.di.module.AppModule;
import com.gmail.neooxpro.di.module.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ViewModelFactoryModule.class})
public interface AppComponent extends AppContainer {

   ContactListComponent plusContactListContainer();
   ContactDetailsComponent plusContactDetailsContainer();
}
