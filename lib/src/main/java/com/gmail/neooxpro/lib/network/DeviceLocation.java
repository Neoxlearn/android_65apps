package com.gmail.neooxpro.lib.network;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.java.domain.repo.DeviceLocationRepository;
import com.google.android.gms.location.FusedLocationProviderClient;

import javax.inject.Inject;

import io.reactivex.Maybe;

public class DeviceLocation implements DeviceLocationRepository {

    @NonNull
    private final FusedLocationProviderClient client;

    @Inject
    public DeviceLocation(@NonNull FusedLocationProviderClient client) {
        this.client = client;
    }

    @NonNull
    @Override
    @SuppressLint({"CheckResult", "MissingPermission"})
    public Maybe<ContactPoint> getDeviceLocation() {
        return Maybe.create(
                emitter -> {
                    try {
                        client.getLastLocation()
                                .addOnSuccessListener(location -> {
                                    if (location != null) {

                                        emitter.onSuccess(new ContactPoint(location.getLongitude(), location.getLatitude()));
                                    } else {
                                        emitter.onComplete();
                                    }
                                })
                                .addOnFailureListener(emitter::onError);
                    } catch (Throwable throwable) {
                        emitter.onError(throwable);
                    }
                });
    }
}
