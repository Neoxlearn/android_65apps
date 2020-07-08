package com.gmail.neooxpro.lib.ui.delegates;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.neooxpro.java.domain.model.Contact;
import com.gmail.neooxpro.lib.R;
import com.gmail.neooxpro.lib.adapter.ContactItemDecoration;
import com.gmail.neooxpro.lib.adapter.ContactsListAdapter;
import com.gmail.neooxpro.lib.ui.view.ContactDetailsFragment;
import com.gmail.neooxpro.lib.ui.view.ContactListMapFragment;
import com.gmail.neooxpro.lib.ui.viewmodel.ContactListViewModel;

import java.util.List;
import java.util.Objects;

public class ContactListViewDelegate implements ContactsListAdapter.ItemClickListener {

    private static final int DP = 4;
    private RecyclerView recyclerView;
    private ContactsListAdapter adapter;
    private ProgressBar progressBar;
    private List<Contact> contactsList;
    private final ContactListViewModel model;
    private final FragmentActivity fragmentActivity;

    public ContactListViewDelegate(@NonNull ContactListViewModel model, @NonNull FragmentActivity fragmentActivity) {
        this.model = model;
        this.fragmentActivity = fragmentActivity;
    }

    public void setProgressBar(@NonNull LifecycleOwner owner) {
        LiveData<Boolean> progressBarStatus = model.isLoading();
        progressBarStatus.observe(owner, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });
    }

    public void setAdapter(@NonNull List<Contact> contactsList) {
        if (adapter != null) {
            adapter.submitItems(contactsList);
            this.contactsList = contactsList;
        }

    }

    public void initView(@NonNull final View view) {
        progressBar = view.findViewById(R.id.progress_bar_contactList);
        recyclerView = view.findViewById(R.id.contact_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentActivity));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(
                new ContactItemDecoration(dpToPx(DP)));
        adapter = new ContactsListAdapter();
        adapter.setOnClickListener(this::onItemClicked);
        recyclerView.setAdapter(adapter);
    }

    private int dpToPx(int dp) {
        return (int) (dp * fragmentActivity.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onItemClicked(int position) {
        FragmentTransaction ft = Objects.requireNonNull(fragmentActivity)
                .getSupportFragmentManager().beginTransaction();
        ContactDetailsFragment cdf = new ContactDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("args", contactsList.get(position).getId());
        cdf.setArguments(bundle);
        ft
                .replace(R.id.container, cdf)
                .addToBackStack(null)
                .commit();
    }

    @NonNull
    public ContactListViewModel getModel() {
        return model;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.map_item) {
            openContactMapFragment();
            return true;
        }
        return false;
    }

    private void openContactMapFragment() {
        FragmentTransaction ft = Objects.requireNonNull(fragmentActivity)
                .getSupportFragmentManager().beginTransaction();
        ContactListMapFragment cdf = new ContactListMapFragment();
        ft
                .replace(R.id.container, cdf)
                .addToBackStack(null)
                .commit();

    }

    public void searchContact(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(fragmentActivity.getString(R.string.search));
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
    }

    public void onDestroyView() {
        adapter = null;
        recyclerView = null;
        progressBar = null;
    }
}
