package com.gmail.neooxpro;
/* Формирование View контакт листа в виде списка контактов*/
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class ContactListFragment extends ListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContactAdapter contactAdapter = new ContactAdapter(getActivity(), 0 , Contact.contacts);
        setListAdapter(contactAdapter);

    }

        @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.toolB.setTitle("Список контактов");
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ContactDetailsFragment cdf = new ContactDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("args", id);
        cdf.setArguments(bundle);
        ft
                .replace(R.id.container, cdf)
                .addToBackStack(null)
                .commit();
    }



    private class ContactAdapter extends ArrayAdapter<Contact>{

        public ContactAdapter(@NonNull Context context, int resource, @NonNull Contact[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_fragment, null, false);
            }
            TextView nameView = convertView.findViewById(R.id.contactName);
            TextView phoneNumberView = convertView.findViewById(R.id.contactPhone);

            nameView.setText(Contact.contacts[position].getName());
            phoneNumberView.setText(Contact.contacts[position].getPhone());

            return convertView;
        }
    }
}
