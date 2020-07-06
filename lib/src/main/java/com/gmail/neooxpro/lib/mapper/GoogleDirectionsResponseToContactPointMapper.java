package com.gmail.neooxpro.lib.mapper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.lib.network.directions.GoogleDirectionsResponse;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleDirectionsResponseToContactPointMapper
        implements Mapper<GoogleDirectionsResponse, List<ContactPoint>> {

    private static final String TAG = GoogleDirectionsResponseToContactPointMapper
            .class.getSimpleName();

    @NonNull
    @Override
    public List<ContactPoint> map(@NonNull GoogleDirectionsResponse response) {
        String polylineEncoded = "";
        try {
            polylineEncoded = response
                    .getRoutes()
                    .get(0)
                    .getOverviewPolyLine()
                    .getPoints();
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "Google response: ", e);
        }
        List<LatLng> latLngs = Collections.unmodifiableList(PolyUtil.decode(polylineEncoded));
        List<ContactPoint> contactPoints = new ArrayList<>();
        for (LatLng point : latLngs) {
            contactPoints.add(new ContactPoint(point.longitude, point.latitude));
        }
        return contactPoints;
    }
}
