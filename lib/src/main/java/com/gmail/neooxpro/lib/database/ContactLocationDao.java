package com.gmail.neooxpro.lib.database;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface ContactLocationDao {

    @NonNull
    @Query("SELECT * FROM contactLocation")
    Single<List<ContactLocationOrm>> getAll();

    @NonNull
    @Query("SELECT * FROM contactLocation WHERE id = :id")
    Maybe<ContactLocationOrm> getContactInfoById(@NonNull String id);

    @Update
    void updateContactPosition(@NonNull ContactLocationOrm contact);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContactPosition(@NonNull ContactLocationOrm contact);

    @Delete
    void deleteContactPosition(@NonNull ContactLocationOrm contact);

    @Query("DELETE FROM contactLocation")
    void deleteAll();
}
