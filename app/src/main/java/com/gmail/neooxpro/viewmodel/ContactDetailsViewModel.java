package com.gmail.neooxpro.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gmail.neooxpro.model.Contact;
import com.gmail.neooxpro.repo.ContactRepository;
import com.gmail.neooxpro.repo.IssueRepository;


public class ContactDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<Contact> contact;
    private IssueRepository repository;

    public ContactDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository();
    }

    public MutableLiveData<Contact> getData(String id) {
        if (contact == null) {
            contact = new MutableLiveData<>();
            contact = repository.loadContact(getApplication(), id);
        }
        return contact;
    }

}
