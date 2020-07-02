package com.gmail.neooxpro.lib.ui.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

import com.gmail.neooxpro.java.domain.model.ContactPoint;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import javax.inject.Inject;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ContactListMapFragment extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 111;
    private View view;
    private GoogleMap mMap;
    private Toolbar toolbar;
    private boolean mLocationPermissionGranted;
    private static final int DEFAULT_ZOOM = 15;
    private ContactPoint lastKnownLocation;
    private static final int PADDING = 200;
    private boolean startMarkerSelected = false;
    private Marker origin;
    private Marker destination;
    private Polyline routeLine;

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
        toolbar.setTitle(R.string.toolbar_title_map);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.contactsMap);
        getLocationPermission();
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(marker -> {
            if (startMarkerSelected) {
                destination = marker;
                marker.setTitle(getString(R.string.finish_position));
                marker.showInfoWindow();
                if (routeLine != null) {
                    routeLine.remove();
                }
                model.loadRoute(origin.getPosition(),destination.getPosition());
                showRoute();
                startMarkerSelected = false;
            } else {
                origin = marker;
                marker.setTitle(getString(R.string.start_position));
                marker.showInfoWindow();
                startMarkerSelected = true;
            }
            return true;
        });
        configureMap();


    }

    public void configureMap() {
        try {
            if (mLocationPermissionGranted) {
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
                        Toast.makeText(requireContext(), R.string.no_current_location, Toast.LENGTH_SHORT).show();
                    }
                    return false;
                });
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
            getContactsLocations();
        } catch (SecurityException e) {
            throw new SecurityException();
        }
    }

    private void getContactsLocations(){
        model.getLocationForAll();
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
        }
    }

    public void showRoute() {
        LiveData<List<LatLng>> route = model.getRoute();
        route.observe(getViewLifecycleOwner(), polyline ->{
            if (!polyline.isEmpty()) {
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

    private void saveDeviceLocation() {
        model.getDeviceLocation();
        LiveData<ContactPoint> data = model.getLocation();
        data.observe(getViewLifecycleOwner(), contactPosition -> {
            lastKnownLocation = contactPosition;
        });
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
                    configureMap();
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

    @Override
    public void onDetach() {
        super.onDetach();
        toolbar = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        model = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        mMap = null;
    }
}
