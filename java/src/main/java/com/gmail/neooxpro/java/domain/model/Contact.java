package com.gmail.neooxpro.java.domain.model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    private String address;

    private double longitude = 0;
    private double latitude = 0;



    public Contact(String id, String name, ArrayList<String> phoneList, ArrayList<String> emailList, String description, String birthday) {
        this.id = id;
        this.name = name;
        this.phone = setPhone(phoneList);
        this.phone2 = setPhone2(phoneList);
        this.email1 = setEmail1(emailList);
        this.email2 = setEmail2(emailList);
        this.description = description;
        this.birthday = setBirthday(birthday);
    }

    public Contact(String id, String name, ArrayList<String> phoneList) {
        this.id = id;
        this.name = name;
        this.phone = setPhone(phoneList);
        this.phone2 = "";
        this.email1 = "";
        this.email2 = "";
        this.description = "";
        this.birthday = setBirthday("");
    }

    private Calendar setBirthday(String bday){
        if (!bday.equals("")) {
            String[] birthdays = bday.split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.MONTH, Integer.parseInt(birthdays[2]) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(birthdays[3]));
            return calendar;
        } else
            return null;
    }

    private String setEmail1(ArrayList<String> emailList){
        return (emailList.size() > 0) ? emailList.get(0) : "";
    }

    private String setEmail2(ArrayList<String> emailList){
        return (emailList.size() > 1) ? emailList.get(1) : "";
    }

    private String setPhone(ArrayList<String> phoneList){
        return (phoneList.size() > 0) ? phoneList.get(0) : "";

    }

    private String setPhone2(ArrayList<String> phoneList){
        return (phoneList.size() > 1) ? phoneList.get(1) : "";

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

    public String getId(){
        return this.id;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
