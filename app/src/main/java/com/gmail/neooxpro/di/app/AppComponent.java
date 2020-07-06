package com.gmail.neooxpro.di.app;


import com.gmail.neooxpro.di.module.DatabaseModule;
import com.gmail.neooxpro.di.module.MapperModule;
import com.gmail.neooxpro.di.module.NetworkModule;
import com.gmail.neooxpro.lib.di.app.AppContainer;
import com.gmail.neooxpro.di.module.AppModule;
import com.gmail.neooxpro.di.module.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.annotations.NonNull;

@Singleton
@Component(modules = {AppModule.class, ViewModelFactoryModule.class,
        DatabaseModule.class, NetworkModule.class, MapperModule.class})
public interface AppComponent extends AppContainer {

    @NonNull
    ContactListComponent plusContactListContainer();

    @NonNull
    ContactDetailsComponent plusContactDetailsContainer();

    @NonNull
    ContactMapComponent plusContactMapContainer();

    @NonNull
    ContactListMapComponent plusContactListMapContainer();

    @NonNull
    NotificationReceiverComponent plusNotificationReceiverContainer();

}
