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


    public void getContactList(AsyncResponseContact asyncResponse){

        AsyncContactsTask asyncContactsTask = new AsyncContactsTask(asyncResponse);
        asyncContactsTask.execute();
    }

    public void getContactDetailsById(AsyncResponseContactDetails asyncResponse,int id){

        AsyncContactDetailsTask asyncContactDetailsTask = new AsyncContactDetailsTask(asyncResponse, id);
        asyncContactDetailsTask.execute();

    }

    private static class AsyncContactsTask extends AsyncTask<Void, Void, Contact[]> {
        private final WeakReference<AsyncResponseContact> delegate;

        private AsyncContactsTask(AsyncResponseContact asyncResponse) {
            delegate = new WeakReference<AsyncResponseContact>(asyncResponse);
        }

        @Override
        protected Contact[] doInBackground(Void... params) {

            return Contact.contacts;

        }

        @Override
        protected void onPostExecute(Contact[] contacts) {
            super.onPostExecute(contacts);
            AsyncResponseContact contactService = delegate.get();

            if (contactService != null){
                contactService.processFinish(contacts);
            }
        }
    }

    private static class AsyncContactDetailsTask extends AsyncTask<Void, Void, Contact> {
        private final WeakReference<AsyncResponseContactDetails> delegate;
        final int id;

        private AsyncContactDetailsTask(AsyncResponseContactDetails asyncResponse, int id) {
            delegate = new WeakReference<AsyncResponseContactDetails>(asyncResponse);
            this.id = id;
        }

        @Override
        protected Contact doInBackground(Void... params) {

            return Contact.contacts[id];

        }

        @Override
        protected void onPostExecute(Contact contact) {
            super.onPostExecute(contact);
            AsyncResponseContactDetails contactService = delegate.get();
            if (contactService != null){
                contactService.processFinish(contact);
            }

        }

    }
}


