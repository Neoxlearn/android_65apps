package com.gmail.neooxpro.lib.ui.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.java.domain.interactor.MapInteractor;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ContactListMapViewModel extends AndroidViewModel {

    private MutableLiveData<List<LatLng>> contactsPosition;
    private MutableLiveData<ContactPoint> location;
    private MutableLiveData<String> contactAddress;
    private MutableLiveData<List<LatLng>> route;
    @NonNull
    private MapInteractor interactor;
    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public ContactListMapViewModel(@NonNull Application application, @NonNull MapInteractor interactor) {
        super(application);
        if (contactsPosition == null) {
            contactsPosition = new MutableLiveData<>();
        }
        if (location == null) {
            location = new MutableLiveData<>();
        }
        if (contactAddress == null) {
            contactAddress = new MutableLiveData<>();
        }
        if (route == null) {
            route = new MutableLiveData<>();
        }
        this.interactor = interactor;

    }

    public LiveData<ContactPoint> getLocation() {
        return location;
    }

    public LiveData<List<LatLng>> getAllLocations() {
        return contactsPosition;
    }

    public LiveData<List<LatLng>> getRoute() {
        return route;
    }

    public void loadRoute(LatLng origin, LatLng destination) {
        compositeDisposable.add(interactor.getDirections(
                new ContactPoint(origin.longitude, origin.latitude),
                new ContactPoint(destination.longitude, destination.latitude))
                .subscribeOn(Schedulers.io())
                .map(userLocation -> getListLatLngFromPoint(userLocation))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        locations -> route.setValue(locations),
                        throwable -> throwable.getStackTrace())
        );
    }

    public void getDeviceLocation() {
        compositeDisposable.add(interactor.getDeviceLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        location1 -> this.location.setValue(location1),
                        throwable -> throwable.getStackTrace())

        );
    }

    public void getLocationForAll() {
        compositeDisposable.add(interactor.getLocations()
                .subscribeOn(Schedulers.io())
                .map(userLocation -> getListLatLngFromPoint(userLocation))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        locations -> contactsPosition.setValue(locations),
                        throwable -> throwable.getStackTrace())
        );
    }


    private List<LatLng> getListLatLngFromPoint(List<ContactPoint> points) {
        if (points.isEmpty()) return new ArrayList<>();
        else {
            List<LatLng> latLngs = new ArrayList<>();
            for (ContactPoint point : points) {
                latLngs.add(new LatLng(point.getLatitude(), point.getLongitude()));
            }
            return latLngs;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

}
