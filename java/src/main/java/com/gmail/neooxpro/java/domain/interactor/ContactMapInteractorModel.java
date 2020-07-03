package com.gmail.neooxpro.java.domain.interactor;


import com.gmail.neooxpro.java.domain.model.ContactLocation;
import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.java.domain.repo.ContactLocationRepository;
import com.gmail.neooxpro.java.domain.repo.YandexGeoApiService;


import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public class ContactMapInteractorModel implements ContactMapInteractor {

    @NonNull
    private final YandexGeoApiService geoService;

    @NonNull
    private final ContactLocationRepository contactLocationRepository;

    @Inject
    public ContactMapInteractorModel(@NonNull YandexGeoApiService geoService,
                                     @NonNull ContactLocationRepository contactLocationRepository) {
        this.geoService = geoService;
        this.contactLocationRepository = contactLocationRepository;

    }

    @NonNull
    @Override
    public Single<String> getAddress(@NonNull ContactPoint point) {
        return geoService.loadGeoCode(point);
    }

    @NonNull
    @Override
    public Single<ContactLocation> saveAddress(ContactLocation location) {
        return contactLocationRepository.insert(location);
    }

    @NonNull
    @Override
    public Maybe<ContactLocation> getLocationById(String contactId) {
        return contactLocationRepository.getById(contactId);
    }

}
