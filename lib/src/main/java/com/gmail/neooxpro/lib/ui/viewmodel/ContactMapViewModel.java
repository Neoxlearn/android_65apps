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

import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ContactMapViewModel extends AndroidViewModel {

    public MutableLiveData<LatLng> contactPosition;
    public MutableLiveData<Location> location;
    public MutableLiveData<String> contactAddress;
    private static final String TAG = ContactMapFragment.class.getSimpleName();
    @NonNull
    private MapInteractor interactor;
    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PublishSubject<LatLng> subject;

    @Inject
    public ContactMapViewModel(@NonNull Application application, @NonNull MapInteractor interactor) {
        super(application);
        if (contactPosition == null){
            contactPosition = new MutableLiveData<>();
        }
        if (location == null){
            location = new MutableLiveData<>();
        }if (contactAddress == null){
            contactAddress = new MutableLiveData<>();
        }
        subject = PublishSubject.create();
        this.interactor = interactor;


    }

    public LiveData<LatLng> getContactPosition(String id){
        getLocationById(id);
        return contactPosition;
    }

    public LiveData<Location> getLocation(){
        getDeviceLocation();
        return location;
    }

    public LiveData<String> getContactAddress(){
        return contactAddress;
    }

    public void setSubject(LatLng latLng){
        subject.onNext(latLng);
    }

    @SuppressLint("CheckResult")
    public void getAddress(LatLng latLng, String contactId) {
        //noinspection ResultOfMethodCallIgnored
        interactor.getAddress(latLng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(
                        address -> {
                            contactAddress.setValue(address);
                            saveAddress(contactId, latLng, address);
                        },
                        throwable -> {
                            Log.e(TAG, "getAddress: error", throwable);
                        }
                );
        /*compositeDisposable.add(subject.switchMapSingle(query -> interactor.getAddress(query).subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnSubscribe(__ -> loading.setValue(true))
                .subscribe(address -> {
                            contactAddress.setValue(address);
                            //saveAddress(contactId, latLng, address);
                        },
                        throwable -> {
                            throwable.getStackTrace();

                        }));*/
    }

    @SuppressLint("CheckResult")
    public void getLocationById(String contactId) {
        //noinspection ResultOfMethodCallIgnored
        interactor.getLocationById(contactId)
                .doOnSubscribe(compositeDisposable::add)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        location -> {
                            contactPosition.setValue(location);
                            Log.d(TAG, "getLocationById: " + location.toString());
                        },
                        throwable -> Log.e(TAG, "getLocationById: error", throwable),
                        () -> Log.d(TAG, "getLocationById: no location")
                );
    }

    @SuppressLint("CheckResult")
    public void saveAddress(String contactId, LatLng latLng, String address) {
        //noinspection ResultOfMethodCallIgnored
        interactor.saveAddress(contactId, latLng, address)
                .doOnSubscribe(compositeDisposable::add)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.d(TAG, "saveAddress: saved"),
                        throwable -> Log.e(TAG, "saveAddress: error", throwable)
                );
    }


    @SuppressLint("CheckResult")
    private void getDeviceLocation() {
        /*try {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener((OnCompleteListener<Location>) task -> {
                    if (task.isSuccessful()) {
                        mLastKnownLocation = (Location) locationResult.getResult();
                        contactPosition.setValue(new LatLng(mLastKnownLocation.getLatitude(),
                                mLastKnownLocation.getLongitude()));

                    } else {
                        contactPosition.setValue(mDefaultLocation);
                    }
                });

        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }*/
        /*interactor.getDeviceLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(
                        location -> {
                            this.location.setValue(location);
                        }/*,
                        throwable -> getViewState().showError(throwable.getMessage()),
                        () -> {
                            getViewState().showError("No location");
                        }*/

        compositeDisposable.add(interactor.getDeviceLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location1 -> {
                    this.location.setValue(location1);
                })

        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
