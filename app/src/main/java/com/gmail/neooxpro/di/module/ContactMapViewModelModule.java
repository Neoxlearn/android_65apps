package com.gmail.neooxpro.di.module;

import androidx.lifecycle.ViewModel;

import com.gmail.neooxpro.lib.ui.viewmodel.ContactMapViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ContactMapViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ContactMapViewModel.class)
    public abstract ViewModel bindContactMapViewModel(ContactMapViewModel contactMapViewModel);
}
