package com.gmail.neooxpro.lib.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
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

    public ContactLocationOrm(String id, double longitude, double latitude, String address) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }
}
