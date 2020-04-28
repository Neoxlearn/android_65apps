package com.gmail.neooxpro;

public class Contact {
    private String name;
    private String phone;
    private String phone2;
    private String email1;
    private String email2;
    private String description;

    static final Contact[] contacts = {
            new Contact("Семенов Иван Васильевич", "8912232122"),
            new Contact("Захаров Дмитрий Анатольевич", "8783782373", "8939393939",
                    "zahd@empty.com", "dzah@yaya.ru", "Описание контакта")};

    public Contact(String name, String phone, String phone2, String email1, String email2, String description) {
        this.name = name;
        this.phone = phone;
        this.phone2 = phone2;
        this.email1 = email1;
        this.email2 = email2;
        this.description = description;
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
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
}
