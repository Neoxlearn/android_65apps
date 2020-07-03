package com.gmail.neooxpro.lib.database;

import androidx.annotation.NonNull;


import com.gmail.neooxpro.java.domain.model.ContactLocation;
import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.java.domain.repo.ContactLocationRepository;
import com.gmail.neooxpro.lib.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.internal.operators.single.SingleFlatMapCompletable;

public final class ContactLocationRepositoryRoom implements ContactLocationRepository {

    @NonNull
    private final ContactLocationDao contactLocationDao;
    @NonNull
    private final Mapper<ContactLocationOrm, ContactLocation> mapper;

    @Inject
    public ContactLocationRepositoryRoom(@NonNull ContactLocationDao contactLocationDao,
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
            for (ContactLocation location : locations) {
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
    public Single<ContactLocation> insert(ContactLocation contactLocation) {
        return Single.fromCallable(() -> {
            ContactLocationOrm contactLocationOrm = new ContactLocationOrm(contactLocation.getId(),
                    contactLocation.getPoint().getLongitude(), contactLocation.getPoint().getLatitude(),
                    contactLocation.getAddress());
            contactLocationDao.insertContactPosition(contactLocationOrm);
            return contactLocation;
        });
    }

}
