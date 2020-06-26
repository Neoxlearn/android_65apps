package com.gmail.neooxpro.lib.network;

import android.location.Location;

import androidx.annotation.NonNull;

import io.reactivex.Maybe;

public interface DeviceLocationRepository {
    @NonNull
    Maybe<Location> getDeviceLocation();
}
