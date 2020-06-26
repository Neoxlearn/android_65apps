package com.gmail.neooxpro.lib.database;

import androidx.annotation.NonNull;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public final class ContactRepositoryRoom implements ContactRepository {

    @NonNull
    private final ContactLocationDao contactLocationDao;

    @Inject
    public ContactRepositoryRoom(@NonNull ContactLocationDao contactLocationDao) {
        this.contactLocationDao = contactLocationDao;
    }

    @NonNull
    @Override
    public Single<List<ContactLocation>> getAll() {
        return contactLocationDao.getAll();
    }

    @NonNull
    @Override
    public Maybe<ContactLocation> getById(@NonNull String id) {
        return contactLocationDao.getContactInfoById(id);
    }

    @NonNull
    @Override
    public Completable update(@NonNull ContactLocation contact) {
        return Completable.fromAction(() -> contactLocationDao.updateContactInfo(contact));
    }

    @NonNull
    @Override
    public Completable insert(@NonNull ContactLocation contact) {
        return Completable.fromAction(() -> contactLocationDao.insertContactInfo(contact));
    }

    @Override
    public void delete(@NonNull ContactLocation contact) {
        contactLocationDao.deleteContactInfo(contact);
    }

    @NonNull
    @Override
    public Completable deleteAll() {
        return Completable.fromAction(contactLocationDao::deleteAll);
    }
}
