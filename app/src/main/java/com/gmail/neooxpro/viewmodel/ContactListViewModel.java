package com.gmail.neooxpro.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gmail.neooxpro.model.Contact;
import com.gmail.neooxpro.repo.ContactRepository;
import java.util.ArrayList;

public class ContactListViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Contact>> contactList;

    public ContactListViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<Contact>> getData() {
        if (contactList == null) {
            contactList = new MutableLiveData<>();
            ContactRepository contactRepository = new ContactRepository();
            contactList = contactRepository.loadContactList(getApplication());
        }
        return contactList;
    }

}
