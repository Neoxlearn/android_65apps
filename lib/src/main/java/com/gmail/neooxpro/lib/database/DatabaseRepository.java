package com.gmail.neooxpro.lib.database;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface DatabaseRepository <Entity, Id> {

    @NonNull
    Single<List<Entity>> getAll();

    @NonNull
    Maybe<Entity> getById(@NonNull Id id);

    @NonNull
    Completable update(@NonNull Entity entity);

    @NonNull
    Completable insert(@NonNull Entity entity);

    void delete(@NonNull Entity entity);

    @NonNull
    Completable deleteAll();
}
