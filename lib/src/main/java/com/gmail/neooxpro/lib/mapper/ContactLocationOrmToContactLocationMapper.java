package com.gmail.neooxpro.lib.mapper;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.java.domain.model.ContactLocation;
import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.lib.database.ContactLocationOrm;

public class ContactLocationOrmToContactLocationMapper implements Mapper<ContactLocationOrm, ContactLocation> {
    @NonNull
    @Override
    public ContactLocation map(@NonNull ContactLocationOrm contactLocationOrm) {
        return new ContactLocation(contactLocationOrm.getId(),
                contactLocationOrm.getAddress(), new ContactPoint(
                contactLocationOrm.getLongitude(),
                contactLocationOrm.getLatitude())

        );
    }
}
