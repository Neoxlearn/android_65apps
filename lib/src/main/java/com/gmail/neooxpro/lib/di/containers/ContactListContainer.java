package com.gmail.neooxpro.lib.di.containers;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.lib.ui.view.ContactListFragment;

public interface ContactListContainer {
    void inject(@NonNull ContactListFragment contactListFragment);
}
