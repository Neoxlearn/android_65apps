package com.gmail.neooxpro.lib.ui.delegates;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.gmail.neooxpro.java.domain.model.ContactPoint;
import com.gmail.neooxpro.lib.R;
import com.gmail.neooxpro.lib.ui.view.ContactListFragment;
import com.gmail.neooxpro.lib.ui.viewmodel.ContactListMapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class ContactListMapDelegate {
    private final GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 15;
    private ContactPoint lastKnownLocation;
    private static final int PADDING = 200;
    private Polyline routeLine;
    private final ContactListMapViewModel model;
    private final FragmentActivity fragmentActivity;
    private static final String TAG = ContactListFragment
            .class.getSimpleName();

    public ContactListMapDelegate(@NonNull GoogleMap map,
                                  @NonNull ContactListMapViewModel model,
                                  @NonNull FragmentActivity fragmentActivity) {
        this.mMap = map;
        this.model = model;
        this.fragmentActivity = fragmentActivity;
    }

    public void showRoute(@NonNull LifecycleOwner owner) {
        LiveData<List<LatLng>> route = model.getRoute();
        route.observe(owner, polyline -> {
            if (!polyline.isEmpty()) {
                if (routeLine != null) {
                    routeLine.remove();
                }
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.addAll(polyline);
                polylineOptions.color(Color.BLUE);
                routeLine = mMap.addPolyline(polylineOptions);
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (LatLng location : polyline) {
                    builder.include(new LatLng(location.latitude, location.longitude));
                }
                LatLngBounds bounds = builder.build();
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, PADDING));
            }
        });
    }


    public void configureMap(boolean mLocationPermissionGranted, @NonNull LifecycleOwner owner) {
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                saveDeviceLocation(owner);
                mMap.setOnMyLocationButtonClickListener(() -> {
                    if (lastKnownLocation != null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                                lastKnownLocation.getLatitude(),
                                lastKnownLocation.getLongitude()
                        ), DEFAULT_ZOOM));
                    } else {
                        Toast.makeText(fragmentActivity, R.string.no_current_location, Toast.LENGTH_SHORT).show();
                    }
                    return false;
                });
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                Toast.makeText(fragmentActivity, R.string.no_current_location, Toast.LENGTH_SHORT).show();
            }
              getContactsLocations(owner);
        } catch (SecurityException e) {
            Log.e(TAG, "GoogleMap configure: ", e);
        }
    }

    private void getContactsLocations(@NonNull LifecycleOwner owner) {
        model.getLocationForAll();
        LiveData<List<LatLng>> data = model.getAllLocations();
        data.observe(owner, contactsPositions -> {
            addAllMarkers(contactsPositions);
            showAllMarkers(contactsPositions);
        });
    }

    public void addAllMarkers(@NonNull List<LatLng> locations) {
        mMap.clear();
        for (LatLng location : locations) {
            mMap.addMarker(new MarkerOptions().position(location));
        }
    }


    public void showAllMarkers(@NonNull List<LatLng> locations) {
        if (!locations.isEmpty()) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng location : locations) {
                builder.include(new LatLng(location.latitude, location.longitude));
            }
            LatLngBounds bounds = builder.build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, PADDING));
        }
    }

    private void saveDeviceLocation(@NonNull LifecycleOwner owner) {
        model.getDeviceLocation();
        LiveData<ContactPoint> data = model.getLocation();
        data.observe(owner, contactPosition -> {
            lastKnownLocation = contactPosition;
        });
    }
}
