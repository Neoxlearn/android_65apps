package com.gmail.neooxpro.di.module;

import android.content.Context;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.java.domain.model.ContactLocation;
import com.gmail.neooxpro.java.domain.repo.IssueRepository;
import com.gmail.neooxpro.lib.database.ContactLocationOrm;
import com.gmail.neooxpro.java.domain.repo.ContactRepository;
import com.gmail.neooxpro.lib.database.ContactRepositoryRoom;
import com.gmail.neooxpro.lib.di.scope.ContactListMapScope;
import com.gmail.neooxpro.lib.mapper.ContactLocationOrmToContactLocationMapper;
import com.gmail.neooxpro.lib.mapper.Mapper;
import com.gmail.neooxpro.lib.network.DeviceLocation;
import com.gmail.neooxpro.java.domain.repo.DeviceLocationRepository;
import com.gmail.neooxpro.java.domain.interactor.MapInteractor;
import com.gmail.neooxpro.java.domain.interactor.MapInteractorModel;
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
public class ContactListMapModule {
    @ContactListMapScope
    @Provides
    public Mapper<ContactLocationOrm, ContactLocation> provideMapper() {
        return new ContactLocationOrmToContactLocationMapper();
    }

    @ContactListMapScope
    @Provides
    public ContactRepository provideContactInfoRepository(ContactRepositoryRoom repository) {
        return repository;
    }

    @ContactListMapScope
    @Provides
    public YandexGeoApiService provideGeoCodeService(YandexGeoApiServiceRetrofit service) {
        return service;
    }

    @ContactListMapScope
    @Provides
    public GoogleDirectionsService provideGoogleDirectionsService(GoogleDirectionsApiServiceRetrofit service) {
        return service;
    }

    @ContactListMapScope
    @Provides
    public MapInteractor provideInteractor(MapInteractorModel interactor) {
        return interactor;
    }

    @ContactListMapScope
    @Provides
    public FusedLocationProviderClient provideFusedLocationProviderClient(@NonNull Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }

    @ContactListMapScope
    @Provides
    public IssueRepository provideContactRepository(@NonNull ContactsResolver repository) {
        return repository;
    }

    @ContactListMapScope
    @Provides
    public DeviceLocationRepository provideLocationRepository(@NonNull DeviceLocation repository) {
        return repository;
    }
}
