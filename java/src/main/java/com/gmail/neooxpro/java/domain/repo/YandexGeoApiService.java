package com.gmail.neooxpro.java.domain.repo;



import com.gmail.neooxpro.java.domain.model.ContactPoint;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public interface YandexGeoApiService {

   @NonNull
   Single<String> loadGeoCode(@NonNull ContactPoint point);


}
