package com.gmail.neooxpro;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import java.lang.ref.WeakReference;
import java.util.ArrayList;


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

        AsyncContactsTask asyncContactsTask = new AsyncContactsTask(asyncResponse, getApplicationContext());
        asyncContactsTask.execute();
    }

    public void getContactDetailsById(AsyncResponseContactDetails asyncResponse, String id){

        AsyncContactDetailsTask asyncContactDetailsTask = new AsyncContactDetailsTask(asyncResponse, getApplicationContext(), id);
        asyncContactDetailsTask.execute();

    }

    private static class AsyncContactsTask extends AsyncTask<Void, Void, ArrayList<Contact>> {
        private final WeakReference<AsyncResponseContact> delegate;
        private final WeakReference<Context> contextWeakReference;

        private AsyncContactsTask(AsyncResponseContact asyncResponse, Context context) {
            delegate = new WeakReference<>(asyncResponse);
            contextWeakReference = new WeakReference<>(context);
        }

        @Override
        protected ArrayList<Contact> doInBackground(Void... voids) {
            final Context context = contextWeakReference.get();
            if (context != null) {
                return ContactsResolver.getContactsList(context);
            }
            else return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Contact> contacts) {
            super.onPostExecute(contacts);
            AsyncResponseContact contactService = delegate.get();

            if (contactService != null){
                contactService.processFinish(contacts);
            }
        }
    }

    private static class AsyncContactDetailsTask extends AsyncTask<Void, Void, Contact> {
        private final WeakReference<AsyncResponseContactDetails> delegate;
        private final WeakReference<Context> contextWeakReference;

        final String id;

        private AsyncContactDetailsTask(AsyncResponseContactDetails asyncResponse, Context context, String id) {
            delegate = new WeakReference<>(asyncResponse);
            contextWeakReference = new WeakReference<>(context);
            this.id = id;
        }

        @Override
        protected Contact doInBackground(Void... voids) {
            final Context context = contextWeakReference.get();
            if (context != null) {
                return ContactsResolver.findContactById(id, context);
            }
            else return null;
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


