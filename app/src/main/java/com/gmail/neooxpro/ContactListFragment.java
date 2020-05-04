package com.gmail.neooxpro;
/* Формирование View контакт листа в виде списка контактов*/
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;


public class ContactListFragment extends ListFragment implements AsyncResponse {
    static Contact[] contactList;
    private ContactService getContactService = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AsyncContactsTask makeList = new AsyncContactsTask(contactList, this);
        makeList.execute();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getContactService = ((GetContactService) activity).contactServiceForFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getContactService = null;
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

    @Override
    public void processFinish(WeakReference<Contact[]> contactsWeak) {
        if (contactsWeak != null) {
            contactList = contactsWeak.get();
            ContactListFragment.ContactAdapter contactAdapter = new ContactListFragment.ContactAdapter(getActivity(), 0, contactList);
            setListAdapter(contactAdapter);
        }
    }


    private class ContactAdapter extends ArrayAdapter<Contact>{

        public ContactAdapter(@NonNull Context context, int resource, Contact[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_fragment, null, false);
            }
            TextView nameView = convertView.findViewById(R.id.contactName);
            TextView phoneNumberView = convertView.findViewById(R.id.contactPhone);
            nameView.setText(contactList[position].getName());
            phoneNumberView.setText(contactList[position].getPhone());
            return convertView;
        }
    }


    private static class AsyncContactsTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Contact[]> contactsWeak;
        private WeakReference<ContactListFragment> delegate;

        private AsyncContactsTask(Contact[] contactList, ContactListFragment asyncResponse) {
            contactsWeak = new WeakReference<Contact[]>(contactList);
            delegate = new WeakReference<ContactListFragment>(asyncResponse);
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (delegate != null) {
                contactsWeak = delegate.get().getContactService.getContactList();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (delegate != null) {
                delegate.get().processFinish(contactsWeak);
                delegate.clear();
            }

        }
    }


}
