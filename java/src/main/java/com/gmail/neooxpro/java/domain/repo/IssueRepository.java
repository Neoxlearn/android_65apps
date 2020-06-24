package com.gmail.neooxpro.java.domain.repo;

import com.gmail.neooxpro.java.domain.model.Contact;

import java.util.ArrayList;

import io.reactivex.Single;

public interface IssueRepository {
      Single<ArrayList<Contact>> loadContactList(String name);
      Single<Contact> findContactById(String id);
}
