package com.gmail.neooxpro.di.module;

import android.content.Context;

import androidx.room.Room;

import com.gmail.neooxpro.lib.database.AppDatabase;
import com.gmail.neooxpro.lib.database.ContactLocationDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class DatabaseModule {

    private final String database = "contact_db";

    @Singleton
    @Provides
    public AppDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, database)
                .build();
    }

    @Singleton
    @Provides
    public ContactLocationDao provideContactDao(AppDatabase database) {
        return database.getContactDao();
    }
}
