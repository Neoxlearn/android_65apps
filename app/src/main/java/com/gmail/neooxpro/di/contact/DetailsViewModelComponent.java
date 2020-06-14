package com.gmail.neooxpro.di.contact;


import com.gmail.neooxpro.di.scope.ContactsDetailsScope;
import com.gmail.neooxpro.viewmodel.ContactDetailsViewModel;

import dagger.Subcomponent;

@ContactsDetailsScope
@Subcomponent(modules = {DetailsViewModelModule.class})
public interface DetailsViewModelComponent {
    void inject(ContactDetailsViewModel contactDetailsViewModel);
}
