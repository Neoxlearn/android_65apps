package com.gmail.neooxpro.di.app;

import com.gmail.neooxpro.di.module.ContactDetailsModule;
import com.gmail.neooxpro.lib.di.containers.NotificationReceiverContainer;
import com.gmail.neooxpro.lib.di.scope.ContactsDetailsScope;

import dagger.Subcomponent;

@ContactsDetailsScope
@Subcomponent(modules = {ContactDetailsModule.class})
public interface NotificationReceiverComponent extends NotificationReceiverContainer {

}
