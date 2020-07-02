package com.gmail.neooxpro.java.domain.interactor;



import com.gmail.neooxpro.java.domain.model.ContactLocation;
import com.gmail.neooxpro.java.domain.model.ContactPoint;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public interface MapInteractor {
    @NonNull
    Single<String> getAddress(@NonNull ContactPoint point);

    @NonNull
    Completable saveAddress(ContactLocation contactLocation);

    @NonNull
    Maybe<ContactLocation> getLocationById(String contactId);

    @NonNull
    Single<List<ContactPoint>> getLocations();

    @NonNull
    Single<List<ContactPoint>> getDirections(@NonNull ContactPoint origin, @NonNull ContactPoint destination);

    @NonNull
    Maybe<ContactPoint> getDeviceLocation();
}
