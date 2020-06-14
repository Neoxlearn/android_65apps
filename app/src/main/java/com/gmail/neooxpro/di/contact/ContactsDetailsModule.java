package com.gmail.neooxpro.di.contact;

import androidx.lifecycle.ViewModelProvider;

import com.gmail.neooxpro.di.scope.ContactsDetailsScope;
import com.gmail.neooxpro.view.ContactDetailsFragment;
import com.gmail.neooxpro.viewmodel.ContactDetailsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public final class ContactsDetailsModule {

    private final ContactDetailsFragment contactDetailsFragment;

    public ContactsDetailsModule(ContactDetailsFragment contactDetailsFragment){
        this.contactDetailsFragment = contactDetailsFragment;
    }

    @Provides
    @ContactsDetailsScope
    public ContactDetailsViewModel getDetailsViewModel() {
        return new ViewModelProvider(contactDetailsFragment).get(ContactDetailsViewModel.class);
    }

}
