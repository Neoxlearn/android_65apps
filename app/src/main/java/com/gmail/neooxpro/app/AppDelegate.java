package com.gmail.neooxpro.app;

import android.app.Application;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.di.app.AppComponent;
import com.gmail.neooxpro.di.app.DaggerAppComponent;

import com.gmail.neooxpro.di.module.AppModule;


public final class AppDelegate extends Application {

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
        appComponent.inject(this);
    }

    @NonNull
    public AppComponent getAppComponent() {
        if (appComponent == null) {
            initDependencies();
        }
        return appComponent;
    }
}