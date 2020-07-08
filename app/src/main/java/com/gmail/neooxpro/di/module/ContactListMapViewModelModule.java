package com.gmail.neooxpro.di.module;

import androidx.lifecycle.ViewModel;

import com.gmail.neooxpro.lib.ui.viewmodel.ContactListMapViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.reactivex.annotations.NonNull;

@Module
public abstract class ContactListMapViewModelModule {
    @Binds
    @IntoMap
    @NonNull
    @ViewModelKey(ContactListMapViewModel.class)
    public abstract ViewModel bindContactListMapViewModel(@NonNull ContactListMapViewModel contactListMapViewModel);
}
