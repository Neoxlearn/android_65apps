package com.gmail.neooxpro.repo;

import android.content.Context;
import androidx.lifecycle.LiveData;

import com.gmail.neooxpro.model.Contact;

import java.util.ArrayList;

public interface IssueRepository {
     LiveData<ArrayList<Contact>> loadContactList(Context context, String name);
     LiveData<Contact> loadContact(Context context, String id);
}
