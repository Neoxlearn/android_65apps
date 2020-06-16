package com.gmail.neooxpro.di.module;

import androidx.lifecycle.ViewModel;

import com.gmail.neooxpro.viewmodel.ContactDetailsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ContactDetailsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ContactDetailsViewModel.class)
    public abstract ViewModel bindContactDetailsViewModel(ContactDetailsViewModel contactDetailsViewModel);
}
