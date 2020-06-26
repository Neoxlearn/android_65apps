package com.gmail.neooxpro.lib.network;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public interface MapInteractor {
    @NonNull
    Single<String> getAddress(@NonNull LatLng latLng);

    @NonNull
    Completable saveAddress(String contactId, @NonNull LatLng latLng, @NonNull String address);

    @NonNull
    Maybe<LatLng> getLocationById(String contactId);

    @NonNull
    Single<List<LatLng>> getLocations();

    @NonNull
    Single<List<LatLng>> getDirections(@NonNull LatLng origin, @NonNull LatLng destination);

    @NonNull
    Maybe<Location> getDeviceLocation();
}
