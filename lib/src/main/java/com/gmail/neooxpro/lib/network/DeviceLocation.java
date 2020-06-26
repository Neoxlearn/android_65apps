package com.gmail.neooxpro.lib.network;

import android.annotation.SuppressLint;
import android.location.Location;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;

import javax.inject.Inject;

import io.reactivex.Maybe;

public class DeviceLocation implements DeviceLocationRepository{

    @NonNull
    private final FusedLocationProviderClient client;

    @Inject
    public DeviceLocation(@NonNull FusedLocationProviderClient client) {
        this.client = client;
    }

    @NonNull
    @Override
    @SuppressLint({"CheckResult", "MissingPermission"})
    public Maybe<Location> getDeviceLocation() {
        return Maybe.create(
                emitter -> {
                    try {
                        client.getLastLocation()
                                .addOnSuccessListener(location -> {
                                    if (location != null) {
                                        emitter.onSuccess(location);
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
