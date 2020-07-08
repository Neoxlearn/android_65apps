package com.gmail.neooxpro.di.module;

import androidx.lifecycle.ViewModel;

import com.gmail.neooxpro.lib.ui.viewmodel.ContactListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.reactivex.annotations.NonNull;

@Module
public abstract class ContactListViewModelModule {

    @Binds
    @IntoMap
    @NonNull
    @ViewModelKey(ContactListViewModel.class)
    public abstract ViewModel bindContactListViewModel(@NonNull ContactListViewModel contactListViewModel);

}
