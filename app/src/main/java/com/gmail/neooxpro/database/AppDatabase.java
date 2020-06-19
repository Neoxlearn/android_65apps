package com.gmail.neooxpro.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.gmail.neooxpro.model.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao getContactDao();
}
