package com.gmail.neooxpro.lib.database;

import com.gmail.neooxpro.java.domain.model.Contact;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public interface ContactRepository {

    @NonNull
    Single<List<ContactLocation>> getAll();

    @NonNull
    Maybe<ContactLocation> getById(@NonNull String id);

    @NonNull
    Completable insert(@NonNull ContactLocation contact);

    @NonNull
    Completable update(@NonNull ContactLocation contact);

    void delete(@NonNull ContactLocation contact);

    @NonNull
    Completable deleteAll();
}
