package com.gmail.neooxpro.lib.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {ContactLocationOrm.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract @NonNull ContactLocationDao getContactDao();
}
