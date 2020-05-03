package com.gmail.neooxpro;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;


public class ContactService extends Service {
    private final ContactBinder mBinder = new ContactBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class ContactBinder extends Binder {
        ContactService getService(){
            return ContactService.this;
        }
    }


    public void getContactList(){

        ContactListFragment.contactList = Contact.contacts.clone();

    }

    public void getContactDetailById(final View view, final int id){
        new Thread(new Runnable() {
            @Override
            public void run() {

                TextView contactName = view.findViewById(R.id.contactName);
                TextView contactPhone = view.findViewById(R.id.contactPhone);
                TextView contactPhone2 = view.findViewById(R.id.contactPhone2);
                TextView contactEmail1 = view.findViewById(R.id.contactMail_1);
                TextView contactEmail2 = view.findViewById(R.id.contactMail_2);
                TextView contactDescription = view.findViewById(R.id.contactDescription);

                Contact contact = Contact.contacts[id];
                contactName.setText(contact.getName());
                contactPhone.setText(contact.getPhone());
                contactPhone2.setText(contact.getPhone2());
                contactEmail1.setText(contact.getEmail1());
                contactEmail2.setText(contact.getEmail2());
                contactDescription.setText(contact.getDescription());

            }
        }).start();


    }


}


