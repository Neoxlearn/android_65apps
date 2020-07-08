package com.gmail.neooxpro.lib.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contactLocation")
public class ContactLocationOrm {

    @NonNull
    @PrimaryKey
    private final String id;
    private final double longitude;
    private final double latitude;
    private final String address;

    public ContactLocationOrm(@NonNull String id, double longitude, double latitude, @Nullable String address) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Nullable
    public String getAddress() {
        return address;
    }
}
