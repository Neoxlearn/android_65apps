package com.gmail.neooxpro.lib.di.app;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.lib.di.containers.ContactDetailsContainer;
import com.gmail.neooxpro.lib.di.containers.ContactListContainer;

import com.gmail.neooxpro.lib.di.containers.ContactListMapContainer;
import com.gmail.neooxpro.lib.di.containers.ContactMapContainer;

import com.gmail.neooxpro.lib.di.containers.NotificationReceiverContainer;


public interface AppContainer {
    @NonNull
    ContactListContainer plusContactListContainer();
    @NonNull
    ContactDetailsContainer plusContactDetailsContainer();
    @NonNull
    ContactMapContainer plusContactMapContainer();
    @NonNull
    ContactListMapContainer plusContactListMapContainer();
    @NonNull
    NotificationReceiverContainer plusNotificationReceiverContainer();

}
