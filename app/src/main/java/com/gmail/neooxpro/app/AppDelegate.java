package com.gmail.neooxpro.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gmail.neooxpro.di.app.AppComponent;
import com.gmail.neooxpro.di.app.DaggerAppComponent;
import com.gmail.neooxpro.lib.di.app.AppContainer;
import com.gmail.neooxpro.lib.di.app.HasAppContainer;
import com.gmail.neooxpro.di.module.AppModule;



public final class AppDelegate extends Application implements HasAppContainer {

    @Nullable
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDependencies();
    }

    private void initDependencies() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @NonNull
    @Override
    public AppContainer appContainer() {
        if (appComponent == null) {
            initDependencies();
        }
        return appComponent;
    }


}
