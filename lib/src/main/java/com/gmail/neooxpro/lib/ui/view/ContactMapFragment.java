package com.gmail.neooxpro.lib.ui.view;

import android.Manifest;
import android.app.Application;
import android.content.Context;

import android.content.pm.PackageManager;
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
import com.gmail.neooxpro.lib.di.app.HasAppContainer;
import com.gmail.neooxpro.lib.di.containers.ContactMapContainer;
import com.gmail.neooxpro.lib.ui.FragmentListener;
import com.gmail.neooxpro.lib.ui.delegates.ContactMapDelegate;
import com.gmail.neooxpro.lib.ui.viewmodel.ContactMapViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.gmail.neooxpro.lib.R;

import javax.inject.Inject;


public class ContactMapFragment extends Fragment implements OnMapReadyCallback {
    private static final int REQUEST_CODE = 111;
    private static final String TAG = ContactMapFragment
            .class.getSimpleName();
    private String id;
    private View view;
    private GoogleMap mMap;
    private Toolbar toolbar;
    private boolean mLocationPermissionGranted;
    private ContactPoint lastKnownLocation;
    private ContactMapDelegate mapDelegate;

    @Inject
    ViewModelProvider.Factory factory;
    private ContactMapViewModel model;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this, factory).get(ContactMapViewModel.class);

    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container, false);
        toolbar.setTitle(R.string.toolbar_title_map);
        id = this.getArguments().getString("args");
        getLocationPermission();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.contactsMap);
        mapFragment.getMapAsync(this);

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            toolbar = ((FragmentListener) context).getToolbar();
        }
        Application app = requireActivity().getApplication();
        if (!(app instanceof HasAppContainer)) {
            throw new IllegalStateException();
        }
        ContactMapContainer contactsMapComponent = ((HasAppContainer) app).appContainer()
                .plusContactMapContainer();
        contactsMapComponent.inject(this);

    }


    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        mMap = map;
        mapDelegate = new ContactMapDelegate(map);
        configureMap();

    }

    private void configureMap() {
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                saveDeviceLocation();
                mMap.setOnMyLocationButtonClickListener(() -> {
                    if (lastKnownLocation != null) {
                        mapDelegate.setCamera(lastKnownLocation);
                    } else {
                        Toast.makeText(requireContext(), R.string.no_current_location, Toast.LENGTH_SHORT).show();
                    }
                    return false;
                });
                getLocationById();

                mMap.setOnMapClickListener(latLng -> {
                    mapDelegate.addMarker(latLng);
                    model.getAddress(latLng, id);
                });
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e(TAG, "GoogleMap configure: ", e);

        }
    }

    public void showAddress(@NonNull String address) {
        Toast.makeText(requireContext(), address, Toast.LENGTH_SHORT).show();
    }


    private void getLocationById() {
        LiveData<LatLng> position = model.getContactPosition(id);
        position.observe(getViewLifecycleOwner(), contactPosition -> {
            mapDelegate.getMarker(contactPosition);
        });

        LiveData<String> address = model.getContactAddress();
        address.observe(getViewLifecycleOwner(), this::showAddress);
    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {
            mLocationPermissionGranted = true;

        }
    }

    public void saveDeviceLocation() {
        model.getDeviceLocation();
        LiveData<ContactPoint> data = model.getLocation();
        data.observe(getViewLifecycleOwner(), contactPosition -> {
            lastKnownLocation = contactPosition;
        });
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int... grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == REQUEST_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            configureMap();
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

    @Override
    public void onDetach() {
        super.onDetach();
        toolbar = null;
    }


}
