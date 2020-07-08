package com.gmail.neooxpro.lib.ui.delegates;


import androidx.annotation.NonNull;

import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContactMapDelegate {
    private final GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 15;

    public ContactMapDelegate(@NonNull GoogleMap mMap) {
        this.mMap = mMap;
    }

    public void setCamera(@NonNull ContactPoint lastKnownLocation) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                lastKnownLocation.getLatitude(),
                lastKnownLocation.getLongitude()
        ), DEFAULT_ZOOM));
    }

    public void getMarker(@NonNull LatLng contactPosition) {
        addMarker(contactPosition);
        showMarker(contactPosition);
    }

    public void addMarker(@NonNull LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
    }

    private void showMarker(@NonNull LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }

}
