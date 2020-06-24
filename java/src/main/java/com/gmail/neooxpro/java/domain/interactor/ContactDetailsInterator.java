package com.gmail.neooxpro.java.domain.interactor;

import com.gmail.neooxpro.java.domain.model.Contact;

import io.reactivex.Single;

public interface ContactDetailsInterator {
    Single<Contact> getContactById(String id);
}
