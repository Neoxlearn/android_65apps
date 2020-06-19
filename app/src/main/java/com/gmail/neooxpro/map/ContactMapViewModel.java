package com.gmail.neooxpro.map;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

public class ContactMapViewModel extends AndroidViewModel {

public MutableLiveData<LatLng> contactPosition;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);

    @Inject
    public ContactMapViewModel(@NonNull Application application) {
        super(application);
        if (contactPosition == null){
            contactPosition = new MutableLiveData<>();
        }
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplication());
        getDeviceLocation();
    }

    public LiveData<LatLng> getData(String id){
        return contactPosition;
    }

    private void getDeviceLocation() {
        try {
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
        }
    }
}
