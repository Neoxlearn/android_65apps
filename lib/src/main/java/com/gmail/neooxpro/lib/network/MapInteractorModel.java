package com.gmail.neooxpro.lib.network;

import android.location.Location;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.java.domain.model.Contact;
import com.gmail.neooxpro.lib.database.ContactLocation;
import com.gmail.neooxpro.lib.database.ContactRepository;
import com.gmail.neooxpro.lib.mapper.Mapper;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class MapInteractorModel implements MapInteractor{

    @NonNull
    private final YandexGeoApiService geoService;

    @NonNull
    private final ContactRepository contactRepository;

    @NonNull
    private final Mapper<ContactLocation, LatLng> mapper;

    @NonNull
    private final DeviceLocationRepository locationRepository;

    @Inject
    public MapInteractorModel(@NonNull YandexGeoApiService geoService,
                             @NonNull ContactRepository contactRepository,
                             @NonNull Mapper<ContactLocation, LatLng> mapper,
                             @NonNull DeviceLocationRepository locationRepository) {
        this.geoService = geoService;
        this.contactRepository = contactRepository;
        this.mapper = mapper;
        this.locationRepository = locationRepository;
    }

    @NonNull
    @Override
    public Single<String> getAddress(@NonNull LatLng latLng) {
        return geoService.loadGeoCode(latLng);
    }

    @NonNull
    @Override
    public Completable saveAddress(String contactId, @NonNull LatLng latLng, @NonNull String address) {
        String longitude = String.valueOf(latLng.longitude);
        String latitude = String.valueOf(latLng.latitude);
        ContactLocation contactLocation = new ContactLocation(contactId, longitude, latitude, address);
        return contactRepository.insert(contactLocation);
    }

    @NonNull
    @Override
    public Maybe<LatLng> getLocationById(String contactId) {
        return contactRepository.getById(contactId)
                .map(mapper::map);
    }

    @NonNull
    @Override
    public Single<List<LatLng>> getLocations() {
        return contactRepository.getAll()
                .map(contactInfoList -> {
                    List<LatLng> locations = new ArrayList<>();
                    for (ContactLocation contactInfo : contactInfoList) {
                        locations.add(mapper.map(contactInfo));
                    }
                    return locations;
                });
    }

    @Override
    public Single<List<LatLng>> getDirections(LatLng origin, LatLng destination) {
        return null;
    }


    @NonNull
    @Override
    public Maybe<Location> getDeviceLocation() {
        return locationRepository.getDeviceLocation();
    }
}
