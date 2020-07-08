package com.gmail.neooxpro.lib.di.containers;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.lib.ui.view.ContactMapFragment;

public interface ContactMapContainer {
    void inject(@NonNull ContactMapFragment contactMapFragment);
}
