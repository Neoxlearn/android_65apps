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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.gmail.neooxpro.lib.ui.viewmodel.ContactDetailsViewModel;


import javax.inject.Inject;


public class ContactDetailsFragment extends Fragment {
    private TextView contactName;
    private TextView contactPhone;
    private TextView contactPhone2;
    private TextView contactEmail1;
    private TextView contactEmail2;
    private TextView contactDescription;
    private TextView contactBirthday;
    private ProgressBar progressBar;
    private Button birthButton;
    private static final int REQUEST_CODE = 1;
    private String contactId;
    private Toolbar toolbar;

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
        progressBar = view.findViewById(R.id.progress_bar_contactDetails);
        toolbar.setTitle(R.string.contactDetails);
        assert this.getArguments() != null;
        contactId = this.getArguments().getString("args");
        contactName = view.findViewById(R.id.contactName);
        contactPhone = view.findViewById(R.id.contactPhone);
        contactPhone2 = view.findViewById(R.id.contactPhone2);
        contactEmail1 = view.findViewById(R.id.contactMail_1);
        contactEmail2 = view.findViewById(R.id.contactMail_2);
        contactDescription = view.findViewById(R.id.contactDescription);
        contactBirthday = view.findViewById(R.id.contactBirthday);
        birthButton = view.findViewById(R.id.birthDayButton);

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
        LiveData<Boolean> progressBarStatus = model.isLoading();
        LiveData<Contact> data = model.getData(id);
        model.haveNotification(id);
        progressBarStatus.observe(getViewLifecycleOwner(),
                isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));
        data.observe(getViewLifecycleOwner(), contact -> {
            contactName.setText(contact.getName());
            contactPhone.setText(contact.getPhone());
            contactPhone2.setText(contact.getPhone2());
            contactEmail1.setText(contact.getEmail1());
            contactEmail2.setText(contact.getEmail2());
            contactDescription.setText(contact.getDescription());

            contactBirthday.setText(String.format(getString(R.string.bTitle), contact.getBirthdayDate()));
            if (contact.getBirthday() != null) {
                notificationProcessing(contact);
            } else {
                birthButton.setText(R.string.noBirthdayDate);
            }
        });
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_details, menu);

    }

    @NonNull
    public ContactDetailsViewModel getModel() {
        return model;
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

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ContactMapFragment cdf = new ContactMapFragment();
        Bundle bundle = new Bundle();
        bundle.putString("args", contactId);
        cdf.setArguments(bundle);
        ft
                .replace(R.id.container, cdf)
                .addToBackStack(null)
                .commit();

    }

    public void notificationProcessing(@NonNull final Contact contact) {
        LiveData<Boolean> haveNotification = model.getNotificationStatus();
        haveNotification.observe(getViewLifecycleOwner(), this::setBirthButtonText);

        birthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getModel().enableOrDisableBirthdayNotification(contact);
            }
        });


    }


    private void setBirthButtonText(boolean haveNotification) {
        if (haveNotification) {
            birthButton.setText(R.string.notificationOn);
        } else {
            birthButton.setText(R.string.notificationOff);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contactName = null;
        contactPhone = null;
        contactPhone2 = null;
        contactEmail1 = null;
        contactEmail2 = null;
        contactDescription = null;
        contactBirthday = null;
        birthButton = null;
        progressBar = null;

    }

}
