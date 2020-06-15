package com.gmail.neooxpro.di.module;

import androidx.lifecycle.ViewModel;

import com.gmail.neooxpro.viewmodel.ContactDetailsViewModel;
import com.gmail.neooxpro.viewmodel.ContactListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactListViewModel.class)
    public abstract ViewModel bindContactListViewModel(ContactListViewModel contactListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ContactDetailsViewModel.class)
    public abstract ViewModel bindContactDetailsViewModel(ContactDetailsViewModel contactDetailsViewModel);
}
