package com.gmail.neooxpro.lib.network.geocode;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexGeoApi {

    String GEO_BASE_URL = "https://geocode-maps.yandex.ru/";

    @GET("1.x?&format=json&")
    Single<YandexGeoResponse> loadAddress(
            @Query("geocode") String latLng,
            @Query("apikey") String key);
}
