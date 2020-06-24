package com.gmail.neooxpro.lib.di.app;

import com.gmail.neooxpro.lib.di.containers.ContactDetailsContainer;
import com.gmail.neooxpro.lib.di.containers.ContactListContainer;
import com.gmail.neooxpro.lib.di.containers.ContactMapContainer;

public interface AppContainer {
    ContactListContainer plusContactListContainer();
    ContactDetailsContainer plusContactDetailsContainer();
    ContactMapContainer plusContactMapContainer();
}
