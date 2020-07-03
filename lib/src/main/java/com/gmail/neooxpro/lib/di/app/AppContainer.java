package com.gmail.neooxpro.lib.di.app;

import com.gmail.neooxpro.lib.di.containers.ContactDetailsContainer;
import com.gmail.neooxpro.lib.di.containers.ContactListContainer;

import com.gmail.neooxpro.lib.di.containers.ContactListMapContainer;
import com.gmail.neooxpro.lib.di.containers.ContactMapContainer;

import com.gmail.neooxpro.lib.di.containers.NotificationReceiverContainer;


public interface AppContainer {
    ContactListContainer plusContactListContainer();
    ContactDetailsContainer plusContactDetailsContainer();

    ContactMapContainer plusContactMapContainer();
    ContactListMapContainer plusContactListMapContainer();

    NotificationReceiverContainer plusNotificationReceiverContainer();

}
