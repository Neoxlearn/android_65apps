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
import com.gmail.neooxpro.lib.ui.view.ContactMapFragment;

import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ContactMapViewModel extends AndroidViewModel {

    public MutableLiveData<LatLng> contactPosition;
    public MutableLiveData<ContactPoint> location;
    public MutableLiveData<String> contactAddress;
    @NonNull
    private ContactMapInteractor interactor;
    @NonNull
    private DeviceLocationInteractor deviceLocationInteractor;
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

    public LiveData<LatLng> getContactPosition(String id) {
        getLocationById(id);
        return contactPosition;
    }

    public LiveData<ContactPoint> getLocation() {
        return location;
    }

    public LiveData<String> getContactAddress() {
        return contactAddress;
    }


    public void getAddress(LatLng latLng, String contactId) {
        compositeDisposable.add(interactor.getAddress(new ContactPoint(latLng.longitude, latLng.latitude))
                .flatMap(address -> interactor.saveAddress(new ContactLocation(contactId, address, new ContactPoint(latLng.longitude, latLng.latitude))))
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

    public void getLocationById(String contactId) {
        compositeDisposable.add(interactor.getLocationById(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        location -> contactPosition.setValue(new LatLng(location.getPoint().getLatitude(), location.getPoint().getLongitude())),
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
