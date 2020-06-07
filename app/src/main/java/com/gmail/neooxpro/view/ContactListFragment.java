package com.gmail.neooxpro.view;
/* Формирование View контакт листа в виде списка контактов*/
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.neooxpro.adapter.ContactItemDecoration;
import com.gmail.neooxpro.adapter.ContactsListAdapter;
import com.gmail.neooxpro.model.Contact;
import com.gmail.neooxpro.FragmentListener;
import com.gmail.neooxpro.R;
import com.gmail.neooxpro.viewmodel.ContactListViewModel;

import java.util.ArrayList;


public class ContactListFragment extends Fragment implements ContactsListAdapter.ItemClickListener {
    private static final int REQUEST_CODE = 1;
    private Toolbar toolbar;
    private ContactListViewModel model;
    private RecyclerView recyclerView;
    private ContactsListAdapter adapter;
    private ArrayList<Contact> contactsList;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(ContactListViewModel.class);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                queryContacts();
            } else
                Toast.makeText(requireContext(), R.string.noPermissions, Toast.LENGTH_LONG).show();
        }
    }

    public void queryContacts(){
        LiveData<Boolean> progressBarStatus = model.isLoading();
        LiveData<ArrayList<Contact>> data = model.getData("");

        progressBarStatus.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null) {
                    progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                }
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initView(view);
        progressBar = view.findViewById(R.id.progress_bar_contactList);
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
        }
        else {
            queryContacts();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener){
            toolbar = ((FragmentListener) context).getToolbar();
        }

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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
               if (model != null) {
                   model.getData(newText);
               }
               return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
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
                new ContactItemDecoration(dpToPx(4)));
        adapter = new ContactsListAdapter();
        adapter.setOnClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private int dpToPx(int dp){
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
