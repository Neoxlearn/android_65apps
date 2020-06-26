package com.gmail.neooxpro.di.module;

import android.content.Context;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.java.domain.repo.IssueRepository;
import com.gmail.neooxpro.lib.database.ContactLocation;
import com.gmail.neooxpro.lib.database.ContactRepository;
import com.gmail.neooxpro.lib.database.ContactRepositoryRoom;
import com.gmail.neooxpro.lib.di.scope.ContactMapScope;
import com.gmail.neooxpro.lib.mapper.ContactLocationToLatLngMapper;
import com.gmail.neooxpro.lib.mapper.Mapper;
import com.gmail.neooxpro.lib.network.DeviceLocation;
import com.gmail.neooxpro.lib.network.DeviceLocationRepository;
import com.gmail.neooxpro.lib.network.MapInteractor;
import com.gmail.neooxpro.lib.network.MapInteractorModel;
import com.gmail.neooxpro.lib.network.YandexGeoApiService;
import com.gmail.neooxpro.lib.network.YandexGeoApiServiceRetrofit;
import com.gmail.neooxpro.lib.service.ContactsResolver;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import dagger.Module;
import dagger.Provides;

@Module
public final class ContactMapModule {

    @ContactMapScope
    @Provides
    public Mapper<ContactLocation, LatLng> provideMapper() {
        return new ContactLocationToLatLngMapper();
    }

    @ContactMapScope
    @Provides
    public ContactRepository provideContactInfoRepository(ContactRepositoryRoom repository) {
        return repository;
    }

    @ContactMapScope
    @Provides
    public YandexGeoApiService provideGeoCodeService(YandexGeoApiServiceRetrofit service) {
        return service;
    }

    /*@ContactMapScope
    @Provides
    public GoogleDirectionsService provideGoogleDirectionsService(GoogleDirectionsServiceRetrofit service) {
        return service;
    }*/

    @ContactMapScope
    @Provides
    public MapInteractor provideInteractor(MapInteractorModel interactor) {
        return interactor;
    }

    @ContactMapScope
    @Provides
    public FusedLocationProviderClient provideFusedLocationProviderClient(@NonNull Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }

    @ContactMapScope
    @Provides
    public IssueRepository provideContactRepository(@NonNull ContactsResolver repository) {
        return repository;
    }

    @ContactMapScope
    @Provides
    public DeviceLocationRepository provideLocationRepository(@NonNull DeviceLocation repository) {
        return repository;
    }
}
