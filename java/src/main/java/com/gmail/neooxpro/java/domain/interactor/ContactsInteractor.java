package com.gmail.neooxpro.java.domain.interactor;

import com.gmail.neooxpro.java.domain.model.Contact;

import java.util.ArrayList;

import io.reactivex.Single;

public interface ContactsInteractor {

    Single<ArrayList<Contact>> getContactList(String name);

}
