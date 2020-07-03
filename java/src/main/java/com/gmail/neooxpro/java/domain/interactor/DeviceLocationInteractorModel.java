package com.gmail.neooxpro.java.domain.interactor;

import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.java.domain.repo.DeviceLocationRepository;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.annotations.NonNull;

public class DeviceLocationInteractorModel implements DeviceLocationInteractor {


    @NonNull
    private final DeviceLocationRepository locationRepository;

    @Inject
    public DeviceLocationInteractorModel(@NonNull DeviceLocationRepository locationRepository) {

        this.locationRepository = locationRepository;
    }

    @NonNull
    @Override
    public Maybe<ContactPoint> getDeviceLocation() {
        return locationRepository.getDeviceLocation();
    }
}
