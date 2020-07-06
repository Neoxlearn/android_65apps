package com.gmail.neooxpro.java.domain.model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.annotations.Nullable;

public class Contact {
    private final String id;
    private final String name;
    private final String phone;
    private final String phone2;
    private final String email1;
    private final String email2;
    private final String description;
    private final Calendar birthday;
    @Nullable
    private final ContactLocation contactLocation;
    private static final String EMPTY_STRING = "";
    private static final int DAY_NUMBER_IN_ARRAY = 3;
    private static final int MONTH_NUMBER_IN_ARRAY = 2;


    public Contact(String id, String name, List<String> phoneList,
                   List<String> emailList, String description, String birthday) {
        this.id = id;
        this.name = name;
        this.phone = setPhone(phoneList);
        this.phone2 = setPhone2(phoneList);
        this.email1 = setEmail1(emailList);
        this.email2 = setEmail2(emailList);
        this.description = description;
        this.birthday = setBirthday(birthday);
        this.contactLocation = null;
    }

    public Contact(String id, String name, List<String> phoneList, List<String> emailList,
                   String description, String birthday, @Nullable ContactLocation contactLocation) {
        this.id = id;
        this.name = name;
        this.phone = setPhone(phoneList);
        this.phone2 = setPhone2(phoneList);
        this.email1 = setEmail1(emailList);
        this.email2 = setEmail2(emailList);
        this.description = description;
        this.birthday = setBirthday(birthday);
        this.contactLocation = contactLocation;
    }

    public Contact(String id, String name, List<String> phoneList) {
        this.id = id;
        this.name = name;
        this.phone = setPhone(phoneList);
        this.phone2 = "";
        this.email1 = "";
        this.email2 = "";
        this.description = "";
        this.birthday = setBirthday("");
        this.contactLocation = null;
    }

    public final Calendar setBirthday(String bday) {
        if (!EMPTY_STRING.equals(bday)) {
            String[] birthdays = bday.split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.MONTH, Integer.parseInt(birthdays[MONTH_NUMBER_IN_ARRAY]) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(birthdays[DAY_NUMBER_IN_ARRAY]));
            return calendar;
        } else {
            return null;
        }
    }

    public final String setEmail1(List<String> emailList) {
        return !emailList.isEmpty() ? emailList.get(0) : "";
    }

    public final String setEmail2(List<String> emailList) {
        return emailList.size() > 1 ? emailList.get(1) : "";
    }

    public final String setPhone(List<String> phoneList) {
        return !phoneList.isEmpty() ? phoneList.get(0) : "";

    }

    public final String setPhone2(List<String> phoneList) {
        return phoneList.size() > 1 ? phoneList.get(1) : "";

    }

    public String getEmail1() {
        return email1;
    }

    public String getEmail2() {
        return email2;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone2() {
        return phone2;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public String getBirthdayDate() {
        if (birthday != null) {
            Date date = birthday.getTime();
            DateFormat formatter = new SimpleDateFormat("dd/MM", Locale.ENGLISH);
            return formatter.format(date);
        } else {
            return "";
        }
    }

    public String getId() {
        return this.id;
    }

    public ContactLocation getContactLocation() {
        return contactLocation;
    }
}
