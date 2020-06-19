package com.gmail.neooxpro.model;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface ContactRepository {

    @NonNull
    Single<List<Contact>> getAll();

    @NonNull
    Maybe<Contact> getById(@NonNull Long id);

    @NonNull
    Completable insert(@NonNull Contact contact);

    @NonNull
    Completable update(@NonNull Contact contact);

    void delete(@NonNull Contact contact);

    @NonNull
    Completable deleteAll();
}
