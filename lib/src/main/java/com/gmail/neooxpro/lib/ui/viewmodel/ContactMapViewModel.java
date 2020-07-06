package com.gmail.neooxpro.lib.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gmail.neooxpro.java.domain.interactor.ContactMapInteractor;
import com.gmail.neooxpro.java.domain.interactor.DeviceLocationInteractor;
import com.gmail.neooxpro.java.domain.model.ContactLocation;
import com.gmail.neooxpro.java.domain.model.ContactPoint;

import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ContactMapViewModel extends AndroidViewModel {

    private MutableLiveData<LatLng> contactPosition;
    private MutableLiveData<ContactPoint> location;
    private MutableLiveData<String> contactAddress;
    @NonNull
    private final ContactMapInteractor interactor;
    @NonNull
    private final DeviceLocationInteractor deviceLocationInteractor;
    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public ContactMapViewModel(@NonNull Application application, @NonNull ContactMapInteractor interactor,
                               @NonNull DeviceLocationInteractor deviceLocationInteractor) {
        super(application);
        if (contactPosition == null) {
            contactPosition = new MutableLiveData<>();
        }
        if (location == null) {
            location = new MutableLiveData<>();
        }
        if (contactAddress == null) {
            contactAddress = new MutableLiveData<>();
        }
        this.interactor = interactor;
        this.deviceLocationInteractor = deviceLocationInteractor;


    }

    @NonNull
    public LiveData<LatLng> getContactPosition(@NonNull String id) {
        getLocationById(id);
        return contactPosition;
    }

    @NonNull
    public LiveData<ContactPoint> getLocation() {
        return location;
    }

    @NonNull
    public LiveData<String> getContactAddress() {
        return contactAddress;
    }


    public void getAddress(@NonNull LatLng latLng, @NonNull String contactId) {
        compositeDisposable.add(interactor.getAddress(
                new ContactPoint(latLng.longitude, latLng.latitude))
                .flatMap(address -> interactor.saveAddress(new ContactLocation(contactId, address,
                        new ContactPoint(latLng.longitude, latLng.latitude))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        location -> {
                            contactAddress.setValue(location.getAddress());
                        },
                        throwable -> throwable.getStackTrace()
                )
        );
    }

    public void getLocationById(@NonNull String contactId) {
        compositeDisposable.add(interactor.getLocationById(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        location -> contactPosition.setValue(new LatLng(location
                                .getPoint().getLatitude(), location.getPoint().getLongitude())),
                        throwable -> throwable.getStackTrace()
                )
        );
    }

    public void getDeviceLocation() {
        compositeDisposable.add(deviceLocationInteractor.getDeviceLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        location1 -> this.location.setValue(location1),
                        throwable -> throwable.getStackTrace())
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
