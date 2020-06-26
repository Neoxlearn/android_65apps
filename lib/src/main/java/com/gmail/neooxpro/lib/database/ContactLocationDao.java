package com.gmail.neooxpro.lib.database;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.gmail.neooxpro.java.domain.model.Contact;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface ContactLocationDao {

    @NonNull
    @Query("SELECT * FROM contactLocation")
    Single<List<ContactLocation>> getAll();

    @NonNull
    @Query("SELECT * FROM contactLocation WHERE id = :id")
    Maybe<ContactLocation> getContactInfoById(String id);

    @Update
    void updateContactInfo(ContactLocation contact);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContactInfo(ContactLocation contact);

    @Delete
    void deleteContactInfo(ContactLocation contact);

    @Query("DELETE FROM contactLocation")
    void deleteAll();
}
