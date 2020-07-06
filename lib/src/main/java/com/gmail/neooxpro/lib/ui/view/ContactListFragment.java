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
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.neooxpro.lib.di.app.HasAppContainer;
import com.gmail.neooxpro.lib.di.containers.ContactListContainer;
import com.gmail.neooxpro.lib.ui.FragmentListener;
import com.gmail.neooxpro.lib.adapter.ContactItemDecoration;
import com.gmail.neooxpro.lib.adapter.ContactsListAdapter;

import com.gmail.neooxpro.java.domain.model.Contact;
import com.gmail.neooxpro.lib.R;
import com.gmail.neooxpro.lib.ui.viewmodel.ContactListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class ContactListFragment extends Fragment implements ContactsListAdapter.ItemClickListener {
    private static final int REQUEST_CODE = 1;
    private static final int DP = 4;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ContactsListAdapter adapter;
    private List<Contact> contactsList;
    private ProgressBar progressBar;

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
        LiveData<Boolean> progressBarStatus = model.isLoading();
        LiveData<ArrayList<Contact>> data = model.getData();
        model.setSubject("");
        progressBarStatus.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });
        data.observe(getViewLifecycleOwner(), contacts -> {
            if (adapter != null) {
                adapter.submitItems(contacts);
                contactsList = contacts;
            }
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
        initView(view);
        progressBar = view.findViewById(R.id.progress_bar_contactList);
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
        adapter = null;
        recyclerView = null;
        progressBar = null;
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ContactListViewModel contactListViewModel = getModel();
                if (contactListViewModel != null) {
                    contactListViewModel.setSubject(newText);
                }
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.map_item) {
            openContactMapFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openContactMapFragment() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ContactListMapFragment cdf = new ContactListMapFragment();
        ft
                .replace(R.id.container, cdf)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar.setTitle(R.string.contactList);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initView(@NonNull final View view) {
        recyclerView = view.findViewById(R.id.contact_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(
                new ContactItemDecoration(dpToPx(DP)));
        adapter = new ContactsListAdapter();
        adapter.setOnClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private int dpToPx(int dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }

    @Override
    public void onItemClicked(int position) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ContactDetailsFragment cdf = new ContactDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("args", contactsList.get(position).getId());
        cdf.setArguments(bundle);
        ft
                .replace(R.id.container, cdf)
                .addToBackStack(null)
                .commit();
    }

}
