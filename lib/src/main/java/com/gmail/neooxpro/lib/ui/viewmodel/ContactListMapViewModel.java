package com.gmail.neooxpro.lib.ui.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gmail.neooxpro.lib.network.MapInteractor;
import com.gmail.neooxpro.lib.ui.view.ContactMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ContactListMapViewModel extends AndroidViewModel {

    public MutableLiveData<List<LatLng>> contactsPosition;
    public MutableLiveData<Location> location;
    public MutableLiveData<String> contactAddress;
    private static final String TAG = ContactMapFragment.class.getSimpleName();
    private Location mLastKnownLocation;
    @NonNull
    private MapInteractor interactor;
    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public ContactListMapViewModel(@NonNull Application application, @NonNull MapInteractor interactor) {
        super(application);
        if (contactsPosition == null){
            contactsPosition = new MutableLiveData<>();
        }
        if (location == null){
            location = new MutableLiveData<>();
        }if (contactAddress == null){
            contactAddress = new MutableLiveData<>();
        }
        this.interactor = interactor;
        //mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplication());
        // getDeviceLocation();

    }

    public LiveData<Location> getLocation(){
        getDeviceLocation();
        return location;
    }

    public LiveData<List<LatLng>> getAllLocations(){
        getLocationForAll();
        return contactsPosition;
    }

    @SuppressLint("CheckResult")
    private void getDeviceLocation() {

        compositeDisposable.add(interactor.getDeviceLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location1 -> {
                    this.location.setValue(location1);
                })

        );
    }

    @SuppressLint("CheckResult")
    public void getLocationForAll() {
        //noinspection ResultOfMethodCallIgnored
        interactor.getLocations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(
                        locations -> {
                            contactsPosition.setValue(locations);
                        },
                        throwable -> Log.e(TAG, "getLocationForAll: error", throwable)
                );
    }

}
