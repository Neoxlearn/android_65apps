package com.gmail.neooxpro;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds;
import java.util.ArrayList;

class ContactsResolver {

    static ArrayList<Contact> getContactsList(Context context) {
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        final Uri uri = Contacts.CONTENT_URI;

        Cursor myCursor = contentResolver.query(
                uri,
                null,
                null,
                null,
                null);
        if (myCursor != null) {
            while (myCursor.moveToNext()) {
                String id = myCursor.getString(myCursor.getColumnIndex(Contacts._ID));
                String contactName = myCursor.getString(myCursor.getColumnIndex(Contacts.DISPLAY_NAME));
                ArrayList<String> contactPhone = getListPhones(contentResolver, id);
                Contact contact = new Contact(id, contactName, contactPhone);
                contactArrayList.add(contact);
            }
            myCursor.close();
        }
        return contactArrayList;
    }

    static Contact findContactById(String id, Context context) {
        Contact contact = null;
        ContentResolver contentResolver = context.getContentResolver();
        final Uri uri = Contacts.CONTENT_URI;
        final String[] columns = {Contacts.DISPLAY_NAME};
        final String selection = Contacts._ID + " = ?";

        Cursor myCursor = contentResolver.query(
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
            myCursor.close();
        }
        return contact;
    }

    static private ArrayList<String> getListPhones(ContentResolver contentResolver, String id) {
        ArrayList<String> listPhoneNumbers = new ArrayList<>();
        Uri uri = CommonDataKinds.Phone.CONTENT_URI;
        String selection = CommonDataKinds.Phone.CONTACT_ID + " = ?";

        Cursor phoneCursor = contentResolver.query(
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

        Cursor birthdayCursor = contentResolver.query(
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
            birthdayCursor.close();
        }
        return birthday;
    }

    static private String getDescription(ContentResolver contentResolver, String id){
        String description ="";
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String selection = CommonDataKinds.Note.CONTACT_ID + " = ?";

        Cursor descCursor = contentResolver.query(
                uri,
                null,
                selection,
                new String[]{id},
                null
        );
        if (descCursor != null){
            while (descCursor.moveToNext()){
                description = descCursor.getString(descCursor.getColumnIndex(CommonDataKinds.Note.NOTE));
            }
            descCursor.close();
        }
        return description;
    }

    static private ArrayList<String> getEmails(ContentResolver contentResolver, String id) {
        ArrayList<String> emailsList = new ArrayList<>();
        Uri uri = CommonDataKinds.Email.CONTENT_URI;
        String selection = CommonDataKinds.Email.CONTACT_ID + " = ?";

        Cursor emailsCursor = contentResolver.query(
                uri,
                null,
                selection,
                new String[]{id},
                null
        );
        if (emailsCursor != null){
            while (emailsCursor.moveToNext()){
                String email = emailsCursor.getString(emailsCursor.getColumnIndex(CommonDataKinds.Email.DATA));
                emailsList.add(email);
            }
            emailsCursor.close();
        }

        return emailsList;
    }
}


