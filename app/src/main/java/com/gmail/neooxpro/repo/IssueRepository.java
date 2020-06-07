package com.gmail.neooxpro.repo;

import android.content.Context;

import com.gmail.neooxpro.model.Contact;

import java.util.ArrayList;

import io.reactivex.Single;

public interface IssueRepository {
      Single<ArrayList<Contact>> loadContactList(Context context, String name);
      Single<Contact> findContactById(String id, Context context);
}
