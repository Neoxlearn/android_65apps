package com.gmail.neooxpro.java.domain.interactor;

import com.gmail.neooxpro.java.domain.model.Contact;
import com.gmail.neooxpro.java.domain.repo.IssueRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Single;

public class ContactsInteractorImpl implements ContactsInteractor {

    private final IssueRepository repository;

    @Inject
    public ContactsInteractorImpl(IssueRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<ArrayList<Contact>> getContactList(String name) {
        return repository.loadContactList(name);
    }

    @Override
    public Single<Contact> getContactById(String id) {
        return repository.findContactById(id);
    }
}
