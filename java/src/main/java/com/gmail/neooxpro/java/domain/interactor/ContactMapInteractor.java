package com.gmail.neooxpro.java.domain.interactor;



import com.gmail.neooxpro.java.domain.model.ContactLocation;
import com.gmail.neooxpro.java.domain.model.ContactPoint;


import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public interface ContactMapInteractor {
    @NonNull
    Single<String> getAddress(@NonNull ContactPoint point);

    @NonNull
    Single<ContactLocation> saveAddress(ContactLocation contactLocation);

    @NonNull
    Maybe<ContactLocation> getLocationById(String contactId);

}
