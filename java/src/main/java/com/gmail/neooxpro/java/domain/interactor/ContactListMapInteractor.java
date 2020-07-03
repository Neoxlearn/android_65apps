package com.gmail.neooxpro.java.domain.interactor;

import com.gmail.neooxpro.java.domain.model.ContactPoint;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public interface ContactListMapInteractor {
    @NonNull
    Single<List<ContactPoint>> getLocations();

    @NonNull
    Single<List<ContactPoint>> getDirections(@NonNull ContactPoint origin, @NonNull ContactPoint destination);
}
