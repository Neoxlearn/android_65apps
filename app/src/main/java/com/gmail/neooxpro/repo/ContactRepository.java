package com.gmail.neooxpro.repo;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.gmail.neooxpro.model.Contact;
import com.gmail.neooxpro.service.ContactsResolver;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ContactRepository implements IssueRepository{

    private MutableLiveData<ArrayList<Contact>> contactList;
    private MutableLiveData<Contact> contact;

    public LiveData<ArrayList<Contact>> loadContactList(Context context) {
        if (contactList == null) {
            contactList = new MutableLiveData<>();
            AsyncContactsTask asyncContactsTask = new AsyncContactsTask(contactList, context);
            asyncContactsTask.execute();
        }
        return contactList;
    }

    public LiveData<Contact> loadContact(Context context, String id) {
        if (contact == null) {
            contact = new MutableLiveData<>();

            AsyncContactDetailsTask asyncContactDetailsTask = new AsyncContactDetailsTask(contact, context, id);
            asyncContactDetailsTask.execute();
        }
        return contact;
    }


    private static class AsyncContactsTask extends AsyncTask<Void, Void, ArrayList<Contact>> {
        private final MutableLiveData<ArrayList<Contact>> contactList;
        private final WeakReference<Context> contextWeakReference;

        private AsyncContactsTask(MutableLiveData<ArrayList<Contact>> callback, Context context) {
            contactList = callback;
            contextWeakReference = new WeakReference<>(context);
        }

        @Override
        protected ArrayList<Contact> doInBackground(Void... voids) {
            final Context context = contextWeakReference.get();
            if (context != null) {
                return ContactsResolver.getContactsList(context);
            } else return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Contact> contacts) {
            super.onPostExecute(contacts);
            contactList.setValue(contacts);

        }
    }


    private static class AsyncContactDetailsTask extends AsyncTask<Void, Void, Contact> {
        private final MutableLiveData<Contact> delegate;
        private final WeakReference<Context> contextWeakReference;
        private final String id;

        private AsyncContactDetailsTask(MutableLiveData<Contact> contactMutableLiveData, Context context, String id) {
            delegate = contactMutableLiveData;
            contextWeakReference = new WeakReference<>(context);
            this.id = id;
        }

        @Override
        protected Contact doInBackground(Void... voids) {
            final Context context = contextWeakReference.get();
            if (context != null) {
              return ContactsResolver.findContactById(id, context);
            } else return null;
        }

        @Override
        protected void onPostExecute(Contact contact) {
            super.onPostExecute(contact);
            delegate.setValue(contact);

        }
    }

}
