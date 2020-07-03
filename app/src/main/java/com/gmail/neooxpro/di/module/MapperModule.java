package com.gmail.neooxpro.di.module;

import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.lib.mapper.GoogleDirectionsResponseToContactPointMapper;
import com.gmail.neooxpro.lib.mapper.Mapper;
import com.gmail.neooxpro.lib.mapper.YandexGeoResponseToString;
import com.gmail.neooxpro.lib.network.directions.GoogleDirectionsResponse;
import com.gmail.neooxpro.lib.network.geocode.YandexGeoResponse;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class MapperModule {

    @Singleton
    @Provides
    public Mapper<YandexGeoResponse, String> provideGeoCodeMapper() {
        return new YandexGeoResponseToString();
    }

    @Singleton
    @Provides
    public Mapper<GoogleDirectionsResponse, List<ContactPoint>> provideDirectionsMapper() {
        return new GoogleDirectionsResponseToContactPointMapper();
    }
}