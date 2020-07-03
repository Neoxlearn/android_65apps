package com.gmail.neooxpro.lib.mapper;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.lib.network.directions.GoogleDirectionsResponse;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleDirectionsResponseToContactPointMapper implements Mapper<GoogleDirectionsResponse, List<ContactPoint>> {

    @NonNull
    @Override
    public List<ContactPoint> map(GoogleDirectionsResponse response) {
        String polylineEncoded = "";
        try {
             polylineEncoded = response
                    .getRoutes()
                    .get(0)
                    .getOverviewPolyLine()
                    .getPoints();
        } catch (IndexOutOfBoundsException e){
            e.getStackTrace();
        }
        List<LatLng> latLngs = Collections.unmodifiableList(PolyUtil.decode(polylineEncoded));
        List<ContactPoint> contactPoints = new ArrayList<>();
        for (LatLng point: latLngs
             ) {
            contactPoints.add(new ContactPoint(point.longitude, point.latitude));
        }
        return contactPoints;
    }
}
