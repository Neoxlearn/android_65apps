package com.gmail.neooxpro.lib.mapper;

import io.reactivex.annotations.NonNull;

public interface Mapper<From, To> {

    @NonNull
    To map(@NonNull From object);
}
