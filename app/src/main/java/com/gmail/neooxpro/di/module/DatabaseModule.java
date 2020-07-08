package com.gmail.neooxpro.di.module;

import android.content.Context;

import androidx.room.Room;

import com.gmail.neooxpro.lib.database.AppDatabase;
import com.gmail.neooxpro.lib.database.ContactLocationDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;

@Module
public final class DatabaseModule {

    private static final String DATABASE = "contact_db";

    @Singleton
    @Provides
    @NonNull
    public AppDatabase provideDatabase(@NonNull Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE)
                .build();
    }

    @Singleton
    @Provides
    @NonNull
    public ContactLocationDao provideContactDao(@NonNull AppDatabase database) {
        return database.getContactDao();
    }
}
