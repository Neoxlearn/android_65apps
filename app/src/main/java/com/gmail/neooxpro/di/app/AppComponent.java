package com.gmail.neooxpro.di.app;


import android.app.Application;

import com.gmail.neooxpro.app.AppDelegate;

import com.gmail.neooxpro.di.module.AppModule;
import com.gmail.neooxpro.di.module.FragmentBuilderModule;
import com.gmail.neooxpro.di.module.ViewModelFactoryModule;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AppModule.class, AndroidSupportInjectionModule.class, FragmentBuilderModule.class, ViewModelFactoryModule.class})
public interface AppComponent extends AndroidInjector<AppDelegate> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
