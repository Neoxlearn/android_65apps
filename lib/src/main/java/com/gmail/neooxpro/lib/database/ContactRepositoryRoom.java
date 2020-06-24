package com.gmail.neooxpro.lib.database;
/*
import androidx.annotation.NonNull;


import com.gmail.neooxpro.java.domain.model.Contact;
import com.gmail.neooxpro.java.domain.repo.ContactRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public final class ContactRepositoryRoom implements ContactRepository {

    @NonNull
    private final ContactDao contactDao;

    @Inject
    public ContactRepositoryRoom(@NonNull ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @NonNull
    @Override
    public Single<List<Contact>> getAll() {
        return contactDao.getAll();
    }

    @NonNull
    @Override
    public Maybe<Contact> getById(@NonNull Long id) {
        return contactDao.getContactInfoById(id);
    }

    @NonNull
    @Override
    public Completable update(@NonNull Contact contact) {
        return Completable.fromAction(() -> contactDao.updateContactInfo(contact));
    }

    @NonNull
    @Override
    public Completable insert(@NonNull Contact contact) {
        return Completable.fromAction(() -> contactDao.insertContactInfo(contact));
    }

    @Override
    public void delete(@NonNull Contact contact) {
        contactDao.deleteContactInfo(contact);
    }

    @NonNull
    @Override
    public Completable deleteAll() {
        return Completable.fromAction(contactDao::deleteAll);
    }
}*/
