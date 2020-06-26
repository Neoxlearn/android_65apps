package com.gmail.neooxpro.lib.mapper;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.java.domain.model.Contact;
import com.gmail.neooxpro.lib.database.ContactLocation;
import com.google.android.gms.maps.model.LatLng;

public class ContactLocationToLatLngMapper implements Mapper<ContactLocation,LatLng> {
    @NonNull
    @Override
    public LatLng map(@NonNull ContactLocation contactLocation) {
        return new LatLng(
                Double.parseDouble(contactLocation.getLatitude()),
                Double.parseDouble(contactLocation.getLongitude())
        );
    }
}
