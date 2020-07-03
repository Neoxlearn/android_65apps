package com.gmail.neooxpro.lib.network.directions;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleDirectionsApi {
    String GOOGLE_BASE_URL = "https://maps.googleapis.com/";

    @GET("maps/api/directions/json")
    Single<GoogleDirectionsResponse> getDirections(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("mode") String mode,
            @Query("key") String key);
}
