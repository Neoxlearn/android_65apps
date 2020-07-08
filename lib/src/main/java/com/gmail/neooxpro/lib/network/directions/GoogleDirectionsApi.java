package com.gmail.neooxpro.lib.network.directions;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

@SuppressWarnings("PMD")
public interface GoogleDirectionsApi {
    String GOOGLE_BASE_URL = "https://maps.googleapis.com/";

    @GET("maps/api/directions/json")
    @NonNull
    Single<GoogleDirectionsResponse> getDirections(
            @Query("origin") @NonNull String origin,
            @Query("destination") @NonNull String destination,
            @Query("mode") @NonNull String mode,
            @Query("key") @NonNull String key);
}
