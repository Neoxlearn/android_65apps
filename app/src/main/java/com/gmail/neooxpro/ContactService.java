package com.gmail.neooxpro;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import java.lang.ref.WeakReference;


public class ContactService extends Service {
    private final ContactBinder mBinder = new ContactBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public class ContactBinder extends Binder {
        ContactService getService(){
            return ContactService.this;
        }
    }


    public void getContactList(ContactListFragment fragment){

        AsyncContactsTask asyncContactsTask = new AsyncContactsTask(fragment);
        asyncContactsTask.execute();
    }

    public void getContactDetailsById(ContactDetailsFragment fragment,int id){

        AsyncContactDetailsTask asyncContactDetailsTask = new AsyncContactDetailsTask(fragment, id);
        asyncContactDetailsTask.execute();

    }

    private static class AsyncContactsTask extends AsyncTask<Void, Void, Contact[]> {
        private WeakReference<ContactListFragment> delegate;

        private AsyncContactsTask(ContactListFragment asyncResponse) {
            delegate = new WeakReference<ContactListFragment>(asyncResponse);
        }

        @Override
        protected Contact[] doInBackground(Void... params) {

            return Contact.contacts;

        }

        @Override
        protected void onPostExecute(Contact[] contacts) {
            super.onPostExecute(contacts);
            ContactListFragment contactService = delegate.get();
            if (contactService != null){
                contactService.processFinish(contacts);
            }
        }
    }

    private static class AsyncContactDetailsTask extends AsyncTask<Void, Void, Contact> {
        private WeakReference<ContactDetailsFragment> delegate;
        int id;

        private AsyncContactDetailsTask(ContactDetailsFragment asyncResponse, int id) {
            delegate = new WeakReference<ContactDetailsFragment>(asyncResponse);
            this.id = id;
        }

        @Override
        protected Contact doInBackground(Void... params) {

            return Contact.contacts[id];

        }

        @Override
        protected void onPostExecute(Contact contact) {
            super.onPostExecute(contact);
            ContactDetailsFragment contactService = delegate.get();
            if (contactService != null){
                contactService.processFinish(contact);
            }

        }

    }
}


