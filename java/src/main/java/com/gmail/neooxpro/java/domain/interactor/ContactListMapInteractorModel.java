package com.gmail.neooxpro.java.domain.interactor;

import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.java.domain.repo.ContactLocationRepository;
import com.gmail.neooxpro.java.domain.repo.DeviceLocationRepository;
import com.gmail.neooxpro.java.domain.repo.GoogleDirectionsService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public class ContactListMapInteractorModel implements ContactListMapInteractor {

    @NonNull
    private final ContactLocationRepository contactLocationRepository;

    @NonNull
    private final GoogleDirectionsService googleDirectionsService;

    @Inject
    public ContactListMapInteractorModel(@NonNull ContactLocationRepository contactLocationRepository,
                                         @NonNull GoogleDirectionsService googleDirectionsService) {

        this.contactLocationRepository = contactLocationRepository;
        this.googleDirectionsService = googleDirectionsService;
    }

    @NonNull
    @Override
    public Single<List<ContactPoint>> getLocations() {
        return contactLocationRepository.getAll();
    }

    @NonNull
    @Override
    public Single<List<ContactPoint>> getDirections(ContactPoint origin, ContactPoint destination) {
        return googleDirectionsService.loadDirections(origin, destination);
    }
}
