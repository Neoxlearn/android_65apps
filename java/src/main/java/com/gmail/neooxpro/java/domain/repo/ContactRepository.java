package com.gmail.neooxpro.java.domain.repo;

import com.gmail.neooxpro.java.domain.model.ContactLocation;
import com.gmail.neooxpro.java.domain.model.ContactPoint;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public interface ContactRepository {

    @NonNull
    Single<List<ContactPoint>> getAll();

    @NonNull
    Maybe<ContactLocation> getById(@NonNull String id);

    @NonNull
    Completable insert(@NonNull ContactLocation contactLocation);

}
