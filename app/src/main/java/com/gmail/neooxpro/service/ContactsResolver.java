package com.gmail.neooxpro.service;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds;


import com.gmail.neooxpro.model.Contact;
import com.gmail.neooxpro.repo.IssueRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Single;

public class ContactsResolver implements IssueRepository {

    @Inject
    public ContactsResolver(){

    }

    public Single<ArrayList<Contact>> loadContactList(Context context, String name) {
        return Single.fromCallable(() -> {
            ArrayList<Contact> contactArrayList = new ArrayList<>();
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = Contacts.CONTENT_URI;
            Cursor myCursor = null;
            String selection = null;
            String[] args = null;
            if (!name.isEmpty()) {
                selection = Contacts.DISPLAY_NAME + " LIKE ?";
                args = new String[]{"%" + name + "%"};
            }
            try {
                myCursor = contentResolver.query(
                        uri,
                        null,
                        selection,
                        args,
                        null);

                if (myCursor != null) {
                    while (myCursor.moveToNext()) {
                        String id = myCursor.getString(myCursor.getColumnIndex(Contacts._ID));
                        String contactName = myCursor.getString(myCursor.getColumnIndex(Contacts.DISPLAY_NAME));
                        ArrayList<String> contactPhone = getListPhones(contentResolver, id);
                        Contact contact = new Contact(id, contactName, contactPhone);
                        contactArrayList.add(contact);
                    }
                }
            } finally {
                if (myCursor != null)
                    myCursor.close();
            }

            return contactArrayList;
        });
    }

    public Single<Contact> findContactById(String id, Context context) {
        return Single.fromCallable(() -> {
            Contact contact = null;
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = Contacts.CONTENT_URI;
            String[] columns = {Contacts.DISPLAY_NAME};
            String selection = Contacts._ID + " = ?";
            Cursor myCursor = null;
            try {
                myCursor = contentResolver.query(
                        uri,
                        columns,
                        selection,
                        new String[]{id},
                        null);
                if (myCursor != null) {
                    while (myCursor.moveToNext()) {
                        String contactName = myCursor.getString(myCursor.getColumnIndex(Contacts.DISPLAY_NAME));
                        String birthday = getBirthdayDate(contentResolver, id);
                        String description = getDescription(contentResolver, id);
                        ArrayList<String> phoneNumbers = getListPhones(contentResolver, id);
                        ArrayList<String> emailList = getEmails(contentResolver, id);

                        contact = new Contact(id, contactName, phoneNumbers, emailList, description, birthday);
                    }
                }
            } finally {
                if (myCursor != null)
                    myCursor.close();
            }
            return contact;
        });
    }

    static private ArrayList<String> getListPhones(ContentResolver contentResolver, String id) {
        ArrayList<String> listPhoneNumbers = new ArrayList<>();
        Uri uri = CommonDataKinds.Phone.CONTENT_URI;
        String selection = CommonDataKinds.Phone.CONTACT_ID + " = ?";
        Cursor phoneCursor = null;
        try {
            phoneCursor = contentResolver.query(
                    uri,
                    null,
                    selection,
                    new String[]{id},
                    null);
            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(CommonDataKinds.Phone.NUMBER));
                    listPhoneNumbers.add(phoneNumber);
                }
            }
        } finally {
            if (phoneCursor != null)
                phoneCursor.close();
        }

        return listPhoneNumbers;
    }

    static private String getBirthdayDate(ContentResolver contentResolver, String id) {
        String birthday = "";
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] columns = {CommonDataKinds.Event.DATA};
        String selection = ContactsContract.Data.CONTACT_ID + "=" + id + " AND "
                + ContactsContract.Data.MIMETYPE + "= '"
                + CommonDataKinds.Event.CONTENT_ITEM_TYPE + "' AND "
                + CommonDataKinds.Event.TYPE + "=" + CommonDataKinds.Event.TYPE_BIRTHDAY;

        Cursor birthdayCursor = null;
        try {
            birthdayCursor = contentResolver.query(
                    uri,
                    columns,
                    selection,
                    null,
                    null
            );
            if (birthdayCursor != null) {
                while (birthdayCursor.moveToNext()) {
                    birthday = birthdayCursor.getString(birthdayCursor.getColumnIndex(CommonDataKinds.Event.START_DATE));
                }
            }
        } finally {
            if (birthdayCursor != null)
                birthdayCursor.close();
        }
        return birthday;
    }

    static private String getDescription(ContentResolver contentResolver, String id) {
        String description = "";
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String selection = ContactsContract.Data.CONTACT_ID
                + " = ? AND " + ContactsContract.Data.MIMETYPE
                + " = ?";
        String[] params = new String[]{
                id,
                ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
        Cursor descCursor = null;

        try {
            descCursor = contentResolver.query(
                    uri,
                    null,
                    selection,
                    params,
                    null
            );
            if (descCursor != null) {
                while (descCursor.moveToNext()) {
                    description = descCursor.getString(descCursor.getColumnIndex(CommonDataKinds.Note.NOTE));
                }
            }
        } finally {
            if (descCursor != null)
                descCursor.close();
        }
        if (description == null)
            description = "";

        return description;
    }

    static private ArrayList<String> getEmails(ContentResolver contentResolver, String id) {
        ArrayList<String> emailsList = new ArrayList<>();
        Uri uri = CommonDataKinds.Email.CONTENT_URI;
        String selection = CommonDataKinds.Email.CONTACT_ID + " = ?";
        Cursor emailsCursor = null;
        try {
            emailsCursor = contentResolver.query(
                    uri,
                    null,
                    selection,
                    new String[]{id},
                    null
            );

            if (emailsCursor != null) {
                while (emailsCursor.moveToNext()) {
                    String email = emailsCursor.getString(emailsCursor.getColumnIndex(CommonDataKinds.Email.DATA));
                    emailsList.add(email);
                }
            }
        } finally {
            if (emailsCursor != null)
                emailsCursor.close();
        }

        return emailsList;
    }


}


