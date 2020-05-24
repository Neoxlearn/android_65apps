package com.gmail.neooxpro.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gmail.neooxpro.model.Contact;
import com.gmail.neooxpro.repo.ContactRepository;


public class ContactDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<Contact> contact;

    public ContactDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Contact> getData(String id) {
        if (contact == null) {
            contact = new MutableLiveData<>();
            ContactRepository contactRepository = new ContactRepository();
            contact = contactRepository.loadContact(getApplication(), id);
        }
        return contact;
    }

}
