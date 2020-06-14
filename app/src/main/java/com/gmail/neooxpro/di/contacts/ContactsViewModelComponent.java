package com.gmail.neooxpro.di.contacts;

import com.gmail.neooxpro.di.scope.ContactsListScope;
import com.gmail.neooxpro.viewmodel.ContactListViewModel;

import dagger.Subcomponent;
@ContactsListScope
@Subcomponent(modules = {ContactsViewModelModule.class})
public interface ContactsViewModelComponent {
    void inject(ContactListViewModel contactListViewModel);
}
