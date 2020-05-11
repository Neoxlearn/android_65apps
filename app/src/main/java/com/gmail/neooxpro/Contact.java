package com.gmail.neooxpro;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Contact {
    private final String name;
    private final String phone;
    private final String phone2;
    private final String email1;
    private final String email2;
    private final String description;
    private final Calendar birthday;

    static final Contact[] contacts = {
            new Contact("Семенов Иван ", "8912232122", "", "", "", "", "10 05"),
            new Contact("Захаров Дмитрий Анатольевич", "8783782373", "8939393939",
                    "zahd@empty.com", "dzah@yaya.ru", "Описание контакта", "03 05")};

    public Contact(String name, String phone, String phone2, String email1, String email2, String description, String birthday) {
        this.name = name;
        this.phone = phone;
        this.phone2 = phone2;
        this.email1 = email1;
        this.email2 = email2;
        this.description = description;
        this.birthday = setBirthday(birthday);
    }

    private Calendar setBirthday(String bday){
        if (!bday.equals("")) {
            String[] birthdays = bday.split(" ");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.MONTH, Integer.parseInt(birthdays[1]) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(birthdays[0]));
            return calendar;
        } else
            return null;
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
            DateFormat formatter = new SimpleDateFormat("dd/MM");
            return formatter.format(date);
        }
        else return "";
    }

    public int getId(){
        return Arrays.asList(contacts).indexOf(this);
    }
}
