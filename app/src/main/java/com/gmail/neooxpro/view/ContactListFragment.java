package com.gmail.neooxpro.view;
/* Формирование View контакт листа в виде списка контактов*/
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.gmail.neooxpro.model.Contact;
import com.gmail.neooxpro.FragmentListener;
import com.gmail.neooxpro.R;
import com.gmail.neooxpro.viewmodel.ContactListViewModel;

import java.util.ArrayList;


public class ContactListFragment extends ListFragment {
    private ArrayList<Contact> contactList;
    private static final int REQUEST_CODE = 1;
    private Toolbar toolbar;
    private ContactListViewModel model;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(ContactListViewModel.class);
        if(requireContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
        }
        else {
            queryContacts();
        }
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
        LiveData<ArrayList<Contact>> data = model.getData();
        data.observe(this, new Observer<ArrayList<Contact>>() {
            @Override
            public void onChanged(ArrayList<Contact> contacts) {
                contactList = contacts;
                ContactAdapter contactAdapter = new ContactAdapter(requireActivity(), 0 , contacts);
                setListAdapter(contactAdapter);
            }


        });
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar.setTitle(R.string.contactList);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ContactDetailsFragment cdf = new ContactDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("args", contactList.get(position).getId());
        cdf.setArguments(bundle);
        ft
                .replace(R.id.container, cdf)
                .addToBackStack(null)
                .commit();
    }

     class ContactAdapter extends ArrayAdapter<Contact>{
        private ArrayList<Contact> contacts;

        public ContactAdapter(@NonNull Context context, int resource, ArrayList<Contact> objects) {

            super(context, resource, objects);
            this.contacts = objects;
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_fragment, null, false);
            }

            TextView nameView = convertView.findViewById(R.id.contactName);
            TextView phoneNumberView = convertView.findViewById(R.id.contactPhone);
            nameView.setText(contacts.get(position).getName());
            phoneNumberView.setText(contacts.get(position).getPhone());
            return convertView;
        }
    }


}
