package com.gmail.neooxpro.di.contacts;

import com.gmail.neooxpro.di.scope.ContactsListScope;
import com.gmail.neooxpro.view.ContactListFragment;

import dagger.Subcomponent;

@ContactsListScope
@Subcomponent(modules = {ContactsListModule.class})
public interface ContactsListComponent {
    void inject(ContactListFragment contactListFragment);
}
