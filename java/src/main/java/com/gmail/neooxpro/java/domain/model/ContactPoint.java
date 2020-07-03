package com.gmail.neooxpro.java.domain.model;


public class ContactPoint {

    private final double latitude;
    private final double longitude;

    public ContactPoint(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public double getLongitude() {
        return longitude;
    }


    public double getLatitude() {
        return latitude;
    }
}
