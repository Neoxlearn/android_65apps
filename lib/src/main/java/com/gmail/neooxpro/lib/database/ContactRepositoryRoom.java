package com.gmail.neooxpro.lib.database;

import androidx.annotation.NonNull;


import com.gmail.neooxpro.java.domain.model.ContactLocation;
import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.java.domain.repo.ContactRepository;
import com.gmail.neooxpro.lib.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public final class ContactRepositoryRoom implements ContactRepository {

    @NonNull
    private final ContactLocationDao contactLocationDao;
    @NonNull
    private final Mapper<ContactLocationOrm, ContactLocation> mapper;

    @Inject
    public ContactRepositoryRoom(@NonNull ContactLocationDao contactLocationDao,
                                 @NonNull Mapper<ContactLocationOrm, ContactLocation> mapper) {
        this.contactLocationDao = contactLocationDao;
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public Single<List<ContactPoint>> getAll() {
        return contactLocationDao.getAll().map(contactLocations -> {
            List<ContactLocation> locations = new ArrayList<>();
            for (ContactLocationOrm contactLocationOrm : contactLocations) {
                locations.add(mapper.map(contactLocationOrm));
            }
            List<ContactPoint> points = new ArrayList<>();
            for (ContactLocation location: locations
                 ) {
                points.add(location.getPoint());
            }
            return points;
        });
    }

    @NonNull
    @Override
    public Maybe<ContactLocation> getById(@NonNull String id) {

        return contactLocationDao.getContactInfoById(id).map(mapper::map);
    }


    @NonNull
    @Override
    public Completable insert(ContactLocation contactLocation) {
        ContactLocationOrm contactLocationOrm = new ContactLocationOrm(contactLocation.getId(),
                contactLocation.getPoint().getLongitude(), contactLocation.getPoint().getLatitude(), contactLocation.getAddress());
        return Completable.fromAction(() -> {
            contactLocationDao.insertContactPosition(contactLocationOrm);
        });
    }

}
