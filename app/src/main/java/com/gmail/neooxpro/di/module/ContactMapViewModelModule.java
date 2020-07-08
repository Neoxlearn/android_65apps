package com.gmail.neooxpro.di.module;

import androidx.lifecycle.ViewModel;

import com.gmail.neooxpro.lib.ui.viewmodel.ContactMapViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.reactivex.annotations.NonNull;

@Module
public abstract class ContactMapViewModelModule {
    @Binds
    @IntoMap
    @NonNull
    @ViewModelKey(ContactMapViewModel.class)
    public abstract ViewModel bindContactMapViewModel(@NonNull ContactMapViewModel contactMapViewModel);
}
