package com.gmail.neooxpro.di.module;

import com.gmail.neooxpro.lib.mapper.Mapper;
import com.gmail.neooxpro.lib.mapper.YandexGeoResponseToString;
import com.gmail.neooxpro.lib.network.YandexGeoResponse;
import com.google.android.gms.maps.model.LatLng;

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

    /*@Singleton
    @Provides
    public Mapper<DirectionsResponse, List<LatLng>> provideDirectionsMapper() {
        return new DirectionsResponseToListLatLngMapper();
    }*/
}