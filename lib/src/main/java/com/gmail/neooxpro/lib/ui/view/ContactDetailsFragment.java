package com.gmail.neooxpro.lib.ui.view;
/* Формирование View деталей контакта и заполнение его полей из полученных аргументов*/

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.gmail.neooxpro.java.domain.model.Contact;
import com.gmail.neooxpro.lib.di.app.HasAppContainer;
import com.gmail.neooxpro.lib.di.containers.ContactDetailsContainer;
import com.gmail.neooxpro.lib.ui.FragmentListener;
import com.gmail.neooxpro.lib.R;
import com.gmail.neooxpro.lib.ui.delegates.ContactDetailsViewDelegate;
import com.gmail.neooxpro.lib.ui.viewmodel.ContactDetailsViewModel;


import javax.inject.Inject;


public class ContactDetailsFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    private String contactId;
    private Toolbar toolbar;
    private ContactDetailsViewDelegate viewDelegate;

    @Inject
    ViewModelProvider.Factory factory;

    private ContactDetailsViewModel model;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this, factory).get(ContactDetailsViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.contact_details_fragment, container, false);
        viewDelegate = new ContactDetailsViewDelegate(view, getActivity(), model);
        toolbar.setTitle(R.string.contactDetails);
        assert this.getArguments() != null;
        contactId = this.getArguments().getString("args");

        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
        } else {
            queryContactDetails(contactId);
        }
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                queryContactDetails(contactId);
            } else {
                Toast.makeText(requireContext(), R.string.noPermissions, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void queryContactDetails(@NonNull String id) {
        viewDelegate.setProgressBar(getViewLifecycleOwner());
        LiveData<Contact> data = model.getData(id);
        model.haveNotification(id);
        data.observe(getViewLifecycleOwner(), contact -> {
           viewDelegate.setViews(contact, getViewLifecycleOwner());
        });
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_details, menu);

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
        ContactDetailsContainer contactsDetailsComponent = ((HasAppContainer) app).appContainer()
                .plusContactDetailsContainer();
        contactsDetailsComponent.inject(this);
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

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.map_item) {
            openContactMapFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openContactMapFragment() {

        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ContactMapFragment cdf = new ContactMapFragment();
        Bundle bundle = new Bundle();
        bundle.putString("args", contactId);
        cdf.setArguments(bundle);
        ft
                .replace(R.id.container, cdf)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewDelegate.onDestroyView();


    }

}
