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


import com.gmail.neooxpro.lib.di.app.HasAppContainer;
import com.gmail.neooxpro.lib.di.containers.ContactMapContainer;
import com.gmail.neooxpro.lib.ui.FragmentListener;
import com.gmail.neooxpro.lib.ui.viewmodel.ContactMapViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gmail.neooxpro.lib.R;
import javax.inject.Inject;



public class ContactMapFragment extends Fragment implements OnMapReadyCallback {
    private static final int REQUEST_CODE = 111;
    private String id;
    private View view;
    private GoogleMap mMap;
    private Toolbar toolbar;
    private boolean mLocationPermissionGranted = true;
    private static final int DEFAULT_ZOOM = 15;
    private Location lastKnownLocation;

    @Inject
    ViewModelProvider.Factory factory;
    private ContactMapViewModel model;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this, factory).get(ContactMapViewModel.class);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container, false);
        toolbar.setTitle("Карта");
        id = this.getArguments().getString("args");
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.contactsMap);
        mapFragment.getMapAsync(this);

        return view;
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
        ContactMapContainer contactsMapComponent = ((HasAppContainer)app).appContainer()
                .plusContactMapContainer();
        contactsMapComponent.inject(this);

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        getLocationPermission();
        //updateLocationUI();

        if (mLocationPermissionGranted){
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            saveDeviceLocation();
            map.setOnMyLocationButtonClickListener(() -> {
                if (lastKnownLocation != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
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
            map.setMyLocationEnabled(false);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            lastKnownLocation = null;
            getLocationPermission();
        }
        getLocationById();

        map.setOnMapClickListener(latLng -> {
            addMarker(latLng);
            //model.setSubject(latLng);
            model.getAddress(latLng, id);
            //showAddress(address.getValue());
        });

    }

    public void showAddress(@NonNull String address) {
        Toast.makeText(requireContext(), address, Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "showAddress: ");
    }



    private void getLocationById(){
        LiveData<LatLng> position = model.getContactPosition(id);
        position.observe(getViewLifecycleOwner(), contactPosition -> {
            addMarker(contactPosition);
            showMarker(contactPosition);
            //model.setSubject(contactPosition);
        });

        LiveData<String> address =  model.getContactAddress(id);
        address.observe(getViewLifecycleOwner(), contactAddress ->{
            showAddress(contactAddress);
        });
    }

    public void addMarker(@NonNull LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
       // Log.d(TAG, "addMarker: ");
    }

    public void showMarker(@NonNull LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
        //Log.d(TAG, "showMarker: ");
    }

    private void setCameraPosition(LatLng contactPosition){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(contactPosition, DEFAULT_ZOOM));
        mMap.addMarker(new MarkerOptions().position(contactPosition));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(contactPosition)
                .zoom(15)
                .tilt(20)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate);
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

    public void saveDeviceLocation() {
        LiveData<Location> data = model.getLocation();
        data.observe(getViewLifecycleOwner(), contactPosition -> {
            lastKnownLocation = contactPosition;
        });
        //Log.d(TAG, "saveDeviceLocation: ");
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
        //updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mMap.getUiSettings().setZoomControlsEnabled(false);
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        mMap = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        model = null;
    }

}
