package com.gmail.neooxpro.java.domain.repo;



import com.gmail.neooxpro.java.domain.model.ContactPoint;

import io.reactivex.Maybe;
import io.reactivex.annotations.NonNull;

public interface DeviceLocationRepository {
    @NonNull
    Maybe<ContactPoint> getDeviceLocation();
}
