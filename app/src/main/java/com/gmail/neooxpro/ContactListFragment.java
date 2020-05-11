package com.gmail.neooxpro;
/* Формирование View контакт листа в виде списка контактов*/
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
        import androidx.fragment.app.FragmentTransaction;
        import androidx.fragment.app.ListFragment;


public class ContactListFragment extends ListFragment implements AsyncResponseContact {
    private ContactService getContactService = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AsyncResponseContact asyncResponse = this;
        getContactService.getContactList(asyncResponse);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof  GetContactService) {
            getContactService = ((GetContactService) context).contactServiceForFragment();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getContactService = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.toolB.setTitle(R.string.contactList);
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

    @Override
    public void processFinish(Contact[] contact) {
        ContactAdapter contactAdapter = new ContactAdapter(requireActivity(), 0 , contact);
        setListAdapter(contactAdapter);
    }


    private class ContactAdapter extends ArrayAdapter<Contact>{
        private Contact[] contacts;

        public ContactAdapter(@NonNull Context context, int resource, Contact[] objects) {
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
            nameView.setText(contacts[position].getName());
            phoneNumberView.setText(contacts[position].getPhone());
            return convertView;
        }
    }


}
