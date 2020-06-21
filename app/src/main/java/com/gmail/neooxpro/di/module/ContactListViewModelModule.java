package com.gmail.neooxpro.di.module;

import androidx.lifecycle.ViewModel;

import com.gmail.neooxpro.lib.ui.viewmodel.ContactListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ContactListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactListViewModel.class)
    public abstract ViewModel bindContactListViewModel(ContactListViewModel contactListViewModel);

}
