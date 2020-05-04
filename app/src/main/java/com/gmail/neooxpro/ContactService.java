package com.gmail.neooxpro;

import android.app.Service;
import android.content.Intent;
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


    public WeakReference<Contact[]> getContactList(){
        return new WeakReference<>(Contact.contacts);
    }

    public Contact getContactDetailsById(int id){

        return Contact.contacts[id];
    }


}


