package com.gmail.neooxpro.di.module;

import com.gmail.neooxpro.di.scope.ContactsDetailsScope;
import com.gmail.neooxpro.di.scope.ContactsListScope;
import com.gmail.neooxpro.view.ContactDetailsFragment;
import com.gmail.neooxpro.view.ContactListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @ContactsListScope
    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ContactListFragment contributeContactListFragment();

    @ContactsDetailsScope
    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ContactDetailsFragment contributeContactDetailsFragment();
}
