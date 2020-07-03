package com.gmail.neooxpro.lib.network.directions;

import android.content.Context;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.java.domain.repo.GoogleDirectionsService;
import com.gmail.neooxpro.lib.R;
import com.gmail.neooxpro.lib.mapper.Mapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class GoogleDirectionsApiServiceRetrofit implements GoogleDirectionsService {

    private static final String TRAVEL_MODE = "walking";

    @NonNull
    private final GoogleDirectionsApi googleDirectionsApi;

    @NonNull
    private final Mapper<GoogleDirectionsResponse, List<ContactPoint>> mapper;

    @NonNull
    private final Context context;

    @Inject
    public GoogleDirectionsApiServiceRetrofit(@NonNull GoogleDirectionsApi googleDirectionsApi,
                                           @NonNull Mapper<GoogleDirectionsResponse, List<ContactPoint>> mapper,
                                           @NonNull Context context) {
        this.googleDirectionsApi = googleDirectionsApi;
        this.mapper = mapper;
        this.context = context;
    }

    @NonNull
    @Override
    public Single<List<ContactPoint>> loadDirections(@NonNull ContactPoint origin, @NonNull ContactPoint destination) {
        String originString = String.format("%s,%s", origin.getLatitude(), origin.getLongitude());
        String destinationString = String.format("%s,%s", destination.getLatitude(), destination.getLongitude());
        return googleDirectionsApi
                .getDirections(
                        originString,
                        destinationString,
                        TRAVEL_MODE,
                        context.getString(R.string.google_maps_key))
                .map(mapper::map);
    }
}
