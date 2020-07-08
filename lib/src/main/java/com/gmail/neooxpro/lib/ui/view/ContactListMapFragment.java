package com.gmail.neooxpro.lib.ui.view;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gmail.neooxpro.lib.R;
import com.gmail.neooxpro.lib.di.app.HasAppContainer;
import com.gmail.neooxpro.lib.di.containers.ContactListMapContainer;
import com.gmail.neooxpro.lib.ui.FragmentListener;
import com.gmail.neooxpro.lib.ui.delegates.ContactListMapDelegate;
import com.gmail.neooxpro.lib.ui.viewmodel.ContactListMapViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;


import javax.inject.Inject;

public class ContactListMapFragment extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 111;
    private View view;
    private GoogleMap mMap;
    private Toolbar toolbar;
    private boolean mLocationPermissionGranted;
    private boolean startMarkerSelected = false;
    private Marker origin;
    private Marker destination;
    private ContactListMapDelegate mapDelegate;

    @Inject
    ViewModelProvider.Factory factory;
    private ContactListMapViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this, factory).get(ContactListMapViewModel.class);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container, false);
        toolbar.setTitle(R.string.toolbar_title_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.contactsMap);
        getLocationPermission();
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mapDelegate = new ContactListMapDelegate(googleMap, model, getActivity());
        mMap.setOnMarkerClickListener(marker -> {
            if (startMarkerSelected) {
                destination = marker;
                marker.setTitle(getString(R.string.finish_position));
                marker.showInfoWindow();
                model.loadRoute(origin.getPosition(), destination.getPosition());
                mapDelegate.showRoute(getViewLifecycleOwner());
                startMarkerSelected = false;
            } else {
                origin = marker;
                marker.setTitle(getResources().getString(R.string.start_position));
                marker.showInfoWindow();
                startMarkerSelected = true;
            }
            return true;
        });
        mapDelegate.configureMap(mLocationPermissionGranted,getViewLifecycleOwner());

    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {
            mLocationPermissionGranted = true;

        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int... grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == REQUEST_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            mapDelegate.configureMap(mLocationPermissionGranted, getViewLifecycleOwner());
        }
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
        ContactListMapContainer contactListMapComponent = ((HasAppContainer) app).appContainer()
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
