package com.gmail.neooxpro.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gmail.neooxpro.model.Contact;
import com.gmail.neooxpro.repo.ContactRepository;
import com.gmail.neooxpro.repo.IssueRepository;


public class ContactDetailsViewModel extends AndroidViewModel {

    private IssueRepository repository;

    public ContactDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository();
    }

    public LiveData<Contact> getData(String id) {
        return repository.loadContact(getApplication(), id);
    }

}
