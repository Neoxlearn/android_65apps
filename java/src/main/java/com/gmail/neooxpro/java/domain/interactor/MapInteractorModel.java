package com.gmail.neooxpro.java.domain.interactor;



import com.gmail.neooxpro.java.domain.interactor.MapInteractor;
import com.gmail.neooxpro.java.domain.model.ContactLocation;
import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.java.domain.repo.ContactRepository;
import com.gmail.neooxpro.java.domain.repo.DeviceLocationRepository;
import com.gmail.neooxpro.java.domain.repo.GoogleDirectionsService;
import com.gmail.neooxpro.java.domain.repo.YandexGeoApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public class MapInteractorModel implements MapInteractor {

    @NonNull
    private final YandexGeoApiService geoService;

    @NonNull
    private final ContactRepository contactRepository;

    @NonNull
    private final DeviceLocationRepository locationRepository;

    @NonNull
    private final GoogleDirectionsService googleDirectionsService;

    @Inject
    public MapInteractorModel(@NonNull YandexGeoApiService geoService,
                              @NonNull ContactRepository contactRepository,
                              @NonNull GoogleDirectionsService googleDirectionsService,
                              @NonNull DeviceLocationRepository locationRepository) {
        this.geoService = geoService;
        this.contactRepository = contactRepository;
        this.locationRepository = locationRepository;
        this.googleDirectionsService = googleDirectionsService;
    }

    @NonNull
    @Override
    public Single<String> getAddress(@NonNull ContactPoint point) {
        return geoService.loadGeoCode(point);
    }

    @NonNull
    @Override
    public Completable saveAddress(ContactLocation location) {

        return contactRepository.insert(location);
    }

    @NonNull
    @Override
    public Maybe<ContactLocation> getLocationById(String contactId) {

        return contactRepository.getById(contactId);
    }

    @NonNull
    @Override
    public Single<List<ContactPoint>> getLocations() {
           return contactRepository.getAll();

    }

    @Override
    public Single<List<ContactPoint>> getDirections(ContactPoint origin, ContactPoint destination) {
        return googleDirectionsService.loadDirections(origin, destination);
    }


    @NonNull
    @Override
    public Maybe<ContactPoint> getDeviceLocation() {
        return locationRepository.getDeviceLocation();
    }
}
