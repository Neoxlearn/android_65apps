package com.gmail.neooxpro.lib.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {ContactLocation.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactLocationDao getContactDao();
}
