package com.gmail.neooxpro.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gmail.neooxpro.model.Contact;
import com.gmail.neooxpro.repo.ContactRepository;
import com.gmail.neooxpro.repo.IssueRepository;

import java.util.ArrayList;

public class ContactListViewModel extends AndroidViewModel {

    private IssueRepository repository;

    public ContactListViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository();
    }

    public LiveData<ArrayList<Contact>> getData() {
        return repository.loadContactList(getApplication());
    }

}
