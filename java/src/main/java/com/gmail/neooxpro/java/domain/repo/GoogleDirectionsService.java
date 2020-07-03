package com.gmail.neooxpro.java.domain.repo;


import com.gmail.neooxpro.java.domain.model.ContactPoint;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public interface GoogleDirectionsService {

    @NonNull
    Single<List<ContactPoint>> loadDirections(@NonNull ContactPoint origin, @NonNull ContactPoint destination);
}
