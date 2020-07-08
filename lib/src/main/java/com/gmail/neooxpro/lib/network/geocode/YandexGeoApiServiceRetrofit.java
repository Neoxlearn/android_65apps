package com.gmail.neooxpro.lib.network.geocode;

import android.content.Context;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.java.domain.repo.YandexGeoApiService;
import com.gmail.neooxpro.lib.R;
import com.gmail.neooxpro.lib.mapper.Mapper;

import javax.inject.Inject;

import io.reactivex.Single;

public class YandexGeoApiServiceRetrofit implements YandexGeoApiService {

    @NonNull
    private final YandexGeoApi geoCodeApi;

    @NonNull
    private final Mapper<YandexGeoResponse, String> mapper;
    private final Context context;

    @Inject
    public YandexGeoApiServiceRetrofit(@NonNull YandexGeoApi geoCodeApi,
                                       @NonNull Context context,
                                       @NonNull Mapper<YandexGeoResponse, String> responseMapper) {
        this.geoCodeApi = geoCodeApi;
        this.mapper = responseMapper;
        this.context = context;
    }

    @NonNull
    @Override
    public Single<String> loadGeoCode(@NonNull ContactPoint point) {
        String latLngString = String.format("%s,%s", point.getLongitude(), point.getLatitude());
        return geoCodeApi.loadAddress(latLngString, context.getResources().getString(R.string.yandex_geo_key))
                .map(object -> mapper.map(object));
    }

}
