package com.gmail.neooxpro.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;


import com.gmail.neooxpro.FragmentListener;
import com.gmail.neooxpro.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ContactMapFragment extends DaggerFragment implements OnMapReadyCallback {
    private static final int REQUEST_CODE = 111;
    private String id;
    private View view;
    private GoogleMap mMap;
    private Toolbar toolbar;
    private boolean mLocationPermissionGranted = true;
    private static final int DEFAULT_ZOOM = 15;

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
        id = getArguments().getString("id");
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

    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        getLocationPermission();
        updateLocationUI();
        if (mLocationPermissionGranted){
            LiveData<LatLng> data = model.getData(id);
            data.observe(getViewLifecycleOwner(), this::setCameraPosition);
        }
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
        updateLocationUI();
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
