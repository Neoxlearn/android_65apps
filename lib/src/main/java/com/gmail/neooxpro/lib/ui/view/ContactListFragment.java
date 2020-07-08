package com.gmail.neooxpro.lib.ui.view;
/* Формирование View контакт листа в виде списка контактов*/

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.gmail.neooxpro.lib.di.app.HasAppContainer;
import com.gmail.neooxpro.lib.di.containers.ContactListContainer;
import com.gmail.neooxpro.lib.ui.FragmentListener;

import com.gmail.neooxpro.java.domain.model.Contact;
import com.gmail.neooxpro.lib.R;
import com.gmail.neooxpro.lib.ui.delegates.ContactListViewDelegate;
import com.gmail.neooxpro.lib.ui.viewmodel.ContactListViewModel;

import java.util.ArrayList;

import javax.inject.Inject;


public class ContactListFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    private Toolbar toolbar;
    private ContactListViewDelegate viewDelegate;

    @Inject
    ViewModelProvider.Factory factory;
    private ContactListViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this, factory).get(ContactListViewModel.class);
    }

    @NonNull
    public ContactListViewModel getModel() {
        return model;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                queryContacts();
            } else {
                Toast.makeText(requireContext(), R.string.noPermissions, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void queryContacts() {
        LiveData<ArrayList<Contact>> data = model.getData();
        model.setSubject("");
        viewDelegate.setProgressBar(getViewLifecycleOwner());
        data.observe(getViewLifecycleOwner(), contacts -> {
            viewDelegate.setAdapter(contacts);
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        viewDelegate = new ContactListViewDelegate(model, getActivity());
        viewDelegate.initView(view);
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
        } else {
            queryContacts();
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
        ContactListContainer contactsListComponent = ((HasAppContainer) app).appContainer()
                .plusContactListContainer();
        contactsListComponent.inject(this);

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
        viewDelegate.onDestroyView();
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        viewDelegate.searchContact(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return viewDelegate.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar.setTitle(R.string.contactList);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }


}
