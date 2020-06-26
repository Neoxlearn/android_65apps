package com.gmail.neooxpro.lib.network;

import android.content.Context;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.lib.R;
import com.gmail.neooxpro.lib.mapper.Mapper;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.Single;

public class YandexGeoApiServiceRetrofit implements YandexGeoApiService{

    @NonNull
    private final YandexGeoApi geoCodeApi;

    @NonNull
    private final Mapper<YandexGeoResponse, String> mapper;
    private final Context context;

    @Inject
    public YandexGeoApiServiceRetrofit(@NonNull YandexGeoApi geoCodeApi,
                                       @NonNull Mapper<YandexGeoResponse, String> responseMapper, Context context) {
        this.geoCodeApi = geoCodeApi;
        this.mapper = responseMapper;
        this.context = context;
    }

    @NonNull
    @Override
    public Single<String> loadGeoCode(@NonNull LatLng latLng) {
        String latLngString = String.format("%s,%s", latLng.longitude, latLng.latitude);
        return geoCodeApi.loadAddress(latLngString, context.getResources().getString(R.string.yandex_geo_key))
                .map(object -> mapper.map(object));
    }

}
