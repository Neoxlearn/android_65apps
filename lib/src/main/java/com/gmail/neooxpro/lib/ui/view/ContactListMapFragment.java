package com.gmail.neooxpro.lib.ui.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.gmail.neooxpro.lib.R;
import com.gmail.neooxpro.lib.di.app.HasAppContainer;
import com.gmail.neooxpro.lib.di.containers.ContactListMapContainer;
import com.gmail.neooxpro.lib.di.containers.ContactMapContainer;
import com.gmail.neooxpro.lib.ui.FragmentListener;
import com.gmail.neooxpro.lib.ui.viewmodel.ContactListMapViewModel;
import com.gmail.neooxpro.lib.ui.viewmodel.ContactMapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

public class ContactListMapFragment extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 111;
    private View view;
    private GoogleMap mMap;
    private Toolbar toolbar;
    private boolean mLocationPermissionGranted;
    private static final int DEFAULT_ZOOM = 15;
    private Location lastKnownLocation;
    private static final int PADDING = 200;

    @Inject
    ViewModelProvider.Factory factory;
    ContactListMapViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this, factory).get(ContactListMapViewModel.class);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container, false);
        toolbar.setTitle("Карта");
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.contactsMap);
        mapFragment.getMapAsync(this);

        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocationPermission();
        if (mLocationPermissionGranted){
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            saveDeviceLocation();
            mMap.setOnMyLocationButtonClickListener(() -> {
                if (lastKnownLocation != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                            lastKnownLocation.getLatitude(),
                            lastKnownLocation.getLongitude()
                    ), DEFAULT_ZOOM));
                } else {
                    Toast.makeText(requireContext(), "No current location", Toast.LENGTH_SHORT).show();
                }
                return false;
            });
        }else {
            //Log.d(TAG, "configureMap: request permissions");
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            lastKnownLocation = null;
            getLocationPermission();
        }
        getContactLocations();


    }

    private void getContactLocations(){
        LiveData<List<LatLng>> data = model.getAllLocations();
        data.observe(getViewLifecycleOwner(), contactsPositions ->{
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
            //Log.d(TAG, "showAllMarkers: show markers");
        } else {
            //Log.d(TAG, "showAllMarkers: locations is empty");
        }
    }

    private void saveDeviceLocation() {
        LiveData<Location> data = model.getLocation();
        data.observe(getViewLifecycleOwner(), contactPosition -> {
            lastKnownLocation = contactPosition;
        });
        //Log.d(TAG, "saveDeviceLocation: ");
    }

    private void getLocationPermission() {

        if(ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        else {
            mLocationPermissionGranted = true;
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener){
            toolbar = ((FragmentListener) context).getToolbar();
        }
        Application app = requireActivity().getApplication();
        if (!(app instanceof HasAppContainer)) {
            throw new IllegalStateException();
        }
        ContactListMapContainer contactListMapComponent = ((HasAppContainer)app).appContainer()
                .plusContactListMapContainer();
        contactListMapComponent.inject(this);

    }
}
