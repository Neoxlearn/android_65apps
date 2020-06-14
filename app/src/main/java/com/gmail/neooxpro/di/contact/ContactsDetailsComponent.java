package com.gmail.neooxpro.di.contact;

import com.gmail.neooxpro.di.scope.ContactsDetailsScope;
import com.gmail.neooxpro.view.ContactDetailsFragment;

import dagger.Subcomponent;

@ContactsDetailsScope
@Subcomponent( modules = {ContactsDetailsModule.class})
public interface ContactsDetailsComponent {

    void inject(ContactDetailsFragment contactDetailsFragment);
}
