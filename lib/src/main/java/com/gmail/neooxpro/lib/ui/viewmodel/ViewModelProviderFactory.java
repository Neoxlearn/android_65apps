package com.gmail.neooxpro.lib.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.gmail.neooxpro.lib.mapper.GoogleDirectionsResponseToContactPointMapper;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;


public class ViewModelProviderFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;
    private static final String TAG = GoogleDirectionsResponseToContactPointMapper
            .class.getSimpleName();

    @Inject
    public ViewModelProviderFactory(@NonNull Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends ViewModel> creator = creators.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException(modelClass.getName());
        }
        try {
            return (T) creator.get();
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "ViewModelFactory create ", e);
            throw new IllegalArgumentException(e);
        }
    }
}
