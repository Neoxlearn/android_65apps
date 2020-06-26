package com.gmail.neooxpro.lib.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contactLocation")
public class ContactLocation {

    @NonNull
    @PrimaryKey
    private final String id;
    private final String longitude;
    private final String latitude;
    private final String address;

    public ContactLocation(String id, String longitude, String latitude, String address) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }
}
