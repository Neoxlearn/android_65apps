package com.gmail.neooxpro.java.domain.interactor;

import com.gmail.neooxpro.java.domain.model.ContactPoint;

import io.reactivex.Maybe;
import io.reactivex.annotations.NonNull;

public interface DeviceLocationInteractor {
    @NonNull
    Maybe<ContactPoint> getDeviceLocation();
}
