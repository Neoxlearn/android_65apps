package com.gmail.neooxpro.java.domain.model;

import io.reactivex.annotations.Nullable;

public class ContactLocation {

    @Nullable
    private final String id;

    @Nullable
    private final String address;

    @Nullable
    private final ContactPoint point;

    public ContactLocation(@Nullable String id,
                           @Nullable String address,
                           @Nullable ContactPoint point){
        this.id = id;
        this.address = address;
        this.point = point;
    }

    @Nullable
    public ContactPoint getPoint() {
        return point;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    @Nullable
    public String getId() {
        return id;
    }

}
