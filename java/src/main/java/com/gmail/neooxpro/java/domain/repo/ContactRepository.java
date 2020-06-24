package com.gmail.neooxpro.java.domain.repo;

import com.gmail.neooxpro.java.domain.model.Contact;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

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
