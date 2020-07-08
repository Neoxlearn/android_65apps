package com.gmail.neooxpro.lib.di.containers;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.lib.ui.view.ContactDetailsFragment;

public interface ContactDetailsContainer {
    void inject(@NonNull ContactDetailsFragment contactDetailsFragment);
}
