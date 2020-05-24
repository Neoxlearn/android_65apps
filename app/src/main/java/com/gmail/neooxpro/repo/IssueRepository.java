package com.gmail.neooxpro.repo;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.gmail.neooxpro.model.Contact;

import java.util.ArrayList;

public interface IssueRepository {
     MutableLiveData<ArrayList<Contact>> loadContactList(Context context);
     MutableLiveData<Contact> loadContact(Context context, String id);
}
