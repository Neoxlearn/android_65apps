package com.gmail.neooxpro.di.module;

import android.content.Context;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.java.domain.interactor.ContactListMapInteractor;
import com.gmail.neooxpro.java.domain.interactor.ContactListMapInteractorModel;
import com.gmail.neooxpro.java.domain.interactor.ContactMapInteractor;
import com.gmail.neooxpro.java.domain.interactor.ContactMapInteractorModel;
import com.gmail.neooxpro.java.domain.interactor.DeviceLocationInteractor;
import com.gmail.neooxpro.java.domain.interactor.DeviceLocationInteractorModel;
import com.gmail.neooxpro.java.domain.model.ContactLocation;
import com.gmail.neooxpro.java.domain.repo.ContactLocationRepository;
import com.gmail.neooxpro.java.domain.repo.IssueRepository;
import com.gmail.neooxpro.lib.database.ContactLocationOrm;
import com.gmail.neooxpro.lib.database.ContactLocationRepositoryRoom;
import com.gmail.neooxpro.lib.di.scope.MapScope;
import com.gmail.neooxpro.lib.mapper.ContactLocationOrmToContactLocationMapper;
import com.gmail.neooxpro.lib.mapper.Mapper;
import com.gmail.neooxpro.lib.network.DeviceLocation;
import com.gmail.neooxpro.java.domain.repo.DeviceLocationRepository;
import com.gmail.neooxpro.lib.network.directions.GoogleDirectionsApiServiceRetrofit;
import com.gmail.neooxpro.java.domain.repo.GoogleDirectionsService;
import com.gmail.neooxpro.java.domain.repo.YandexGeoApiService;
import com.gmail.neooxpro.lib.network.geocode.YandexGeoApiServiceRetrofit;
import com.gmail.neooxpro.lib.service.ContactsResolver;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import dagger.Module;
import dagger.Provides;

@Module
public final class MapModule {

    @MapScope
    @Provides
    public Mapper<ContactLocationOrm, ContactLocation> provideMapper() {
        return new ContactLocationOrmToContactLocationMapper();
    }

    @MapScope
    @Provides
    public ContactLocationRepository provideContactInfoRepository(ContactLocationRepositoryRoom repository) {
        return repository;
    }

    @MapScope
    @Provides
    public YandexGeoApiService provideGeoCodeService(YandexGeoApiServiceRetrofit service) {
        return service;
    }

    @MapScope
    @Provides
    public GoogleDirectionsService provideGoogleDirectionsService(GoogleDirectionsApiServiceRetrofit service) {
        return service;
    }

    @MapScope
    @Provides
    public ContactMapInteractor provideContactMapInteractor(ContactMapInteractorModel interactor) {
        return interactor;
    }

    @MapScope
    @Provides
    public ContactListMapInteractor provideContactListInteractor(ContactListMapInteractorModel interactor) {
        return interactor;
    }

    @MapScope
    @Provides
    public DeviceLocationInteractor provideDeviceLocationInteractor(DeviceLocationInteractorModel interactor) {
        return interactor;
    }

    @MapScope
    @Provides
    public FusedLocationProviderClient provideFusedLocationProviderClient(@NonNull Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }

    @MapScope
    @Provides
    public IssueRepository provideContactRepository(@NonNull ContactsResolver repository) {
        return repository;
    }

    @MapScope
    @Provides
    public DeviceLocationRepository provideLocationRepository(@NonNull DeviceLocation repository) {
        return repository;
    }
}
