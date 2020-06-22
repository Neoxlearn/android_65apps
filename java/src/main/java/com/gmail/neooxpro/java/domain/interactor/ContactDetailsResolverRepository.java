package com.gmail.neooxpro.java.domain.interactor;

import com.gmail.neooxpro.java.domain.model.Contact;
import com.gmail.neooxpro.java.domain.repo.IssueRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class ContactDetailsResolverRepository implements ContactDetailsInterator{

    private final IssueRepository repository;

    @Inject
    public ContactDetailsResolverRepository(IssueRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<Contact> getContactById(String id) {
        return repository.findContactById(id);
    }
}
