package com.gmail.neooxpro.di.module;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.gmail.neooxpro.lib.ui.viewmodel.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    @NonNull
    public abstract ViewModelProvider.Factory bindViewModelFactory(@NonNull ViewModelProviderFactory viewModelFactory);

}
