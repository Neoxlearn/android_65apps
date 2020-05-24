package com.gmail.neooxpro.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gmail.neooxpro.model.Contact;
import com.gmail.neooxpro.repo.ContactRepository;
import com.gmail.neooxpro.repo.IssueRepository;

import java.util.ArrayList;

public class ContactListViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Contact>> contactList;
    private IssueRepository repository;

    public ContactListViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository();

    }

    public MutableLiveData<ArrayList<Contact>> getData() {
        if (contactList == null) {
            contactList = new MutableLiveData<>();
            contactList = repository.loadContactList(getApplication());
        }
        return contactList;
    }

}
