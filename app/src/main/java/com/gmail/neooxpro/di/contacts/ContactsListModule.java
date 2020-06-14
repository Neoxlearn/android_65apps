package com.gmail.neooxpro.di.contacts;

import androidx.lifecycle.ViewModelProvider;

import com.gmail.neooxpro.di.scope.ContactsListScope;
import com.gmail.neooxpro.view.ContactListFragment;
import com.gmail.neooxpro.viewmodel.ContactListViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public final class ContactsListModule {

    private final ContactListFragment contactListFragment;

    public ContactsListModule(ContactListFragment contactListFragment){
        this.contactListFragment = contactListFragment;
    }

    @Provides
    @ContactsListScope
    public ContactListViewModel getDetailsViewModel() {
        return new ViewModelProvider(contactListFragment).get(ContactListViewModel.class);
    }
}
