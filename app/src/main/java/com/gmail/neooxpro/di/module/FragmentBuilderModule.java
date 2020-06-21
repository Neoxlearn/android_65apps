package com.gmail.neooxpro.di.module;

import com.gmail.neooxpro.di.scope.ContactsDetailsScope;
import com.gmail.neooxpro.di.scope.ContactsListScope;
import com.gmail.neooxpro.lib.ui.view.ContactDetailsFragment;
import com.gmail.neooxpro.lib.ui.view.ContactListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @ContactsListScope
    @ContributesAndroidInjector(modules = {ContactListViewModelModule.class})
    abstract ContactListFragment contributeContactListFragment();

    @ContactsDetailsScope
    @ContributesAndroidInjector(modules = {ContactDetailsViewModelModule.class})
    abstract ContactDetailsFragment contributeContactDetailsFragment();
}
