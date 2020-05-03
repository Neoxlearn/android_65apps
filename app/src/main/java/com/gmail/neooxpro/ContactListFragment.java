package com.gmail.neooxpro;
/* Формирование View контакт листа в виде списка контактов*/
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



public class ContactListFragment extends ListFragment {
    static Contact[] contactList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyTask makeList = new MyTask();
        makeList.execute();
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


     private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            MainActivity.contactService.getContactList();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ContactAdapter contactAdapter = new ContactAdapter(getActivity(), 0, contactList);
            setListAdapter(contactAdapter);
        }
     }


}
