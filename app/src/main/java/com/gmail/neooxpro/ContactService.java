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


    public void getContactList(AsyncResponseContact asyncResponse, Context context){

        AsyncContactsTask asyncContactsTask = new AsyncContactsTask(asyncResponse);
        asyncContactsTask.execute(context);
    }

    public void getContactDetailsById(AsyncResponseContactDetails asyncResponse,String id, Context context){

        AsyncContactDetailsTask asyncContactDetailsTask = new AsyncContactDetailsTask(asyncResponse, id);
        asyncContactDetailsTask.execute(context);

    }

    private static class AsyncContactsTask extends AsyncTask<Context, Void, ArrayList<Contact>> {
        private final WeakReference<AsyncResponseContact> delegate;

        private AsyncContactsTask(AsyncResponseContact asyncResponse) {
            delegate = new WeakReference<AsyncResponseContact>(asyncResponse);
        }

        @Override
        protected ArrayList<Contact> doInBackground(Context... contexts) {

            return ContactsResolver.getContactsList(contexts[0]);

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

    private static class AsyncContactDetailsTask extends AsyncTask<Context, Void, Contact> {
        private final WeakReference<AsyncResponseContactDetails> delegate;
        final String id;

        private AsyncContactDetailsTask(AsyncResponseContactDetails asyncResponse, String id) {
            delegate = new WeakReference<AsyncResponseContactDetails>(asyncResponse);
            this.id = id;
        }

        @Override
        protected Contact doInBackground(Context... contexts) {

            return ContactsResolver.findContactById(id, contexts[0]);

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


