package com.gmail.neooxpro.di.module;

import androidx.lifecycle.ViewModel;

import com.gmail.neooxpro.lib.ui.viewmodel.ContactListMapViewModel;
import com.gmail.neooxpro.lib.ui.viewmodel.ContactMapViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ContactListMapViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ContactListMapViewModel.class)
    public abstract ViewModel bindContactListMapViewModel(ContactListMapViewModel contactListMapViewModel);
}
