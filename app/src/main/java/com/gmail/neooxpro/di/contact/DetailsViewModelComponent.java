package com.gmail.neooxpro.di.contact;


import com.gmail.neooxpro.viewmodel.ContactDetailsViewModel;

import dagger.Subcomponent;

@Subcomponent(modules = {DetailsViewModelModule.class})
public interface DetailsViewModelComponent {
    void inject(ContactDetailsViewModel contactDetailsViewModel);
}
