package com.gmail.neooxpro.java.domain.model;

import io.reactivex.annotations.Nullable;

public class ContactPoint {

    private final double latitude;
    private final double longitude;

    public ContactPoint(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Nullable
    public double getLongitude() {
        return longitude;
    }

    @Nullable
    public double getLatitude() {
        return latitude;
    }
}
