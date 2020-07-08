package com.gmail.neooxpro.di.module;

import androidx.lifecycle.ViewModel;

import com.gmail.neooxpro.lib.ui.viewmodel.ContactDetailsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.reactivex.annotations.NonNull;

@Module
public abstract class ContactDetailsViewModelModule {
    @Binds
    @IntoMap
    @NonNull
    @ViewModelKey(ContactDetailsViewModel.class)
    public abstract ViewModel bindContactDetailsViewModel(@NonNull ContactDetailsViewModel contactDetailsViewModel);
}
