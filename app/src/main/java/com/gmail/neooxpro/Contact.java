package com.gmail.neooxpro;

public class Contact {
    private final String name;
    private final String phone;
    private final String phone2;
    private final String email1;
    private final String email2;
    private final String description;

    static final Contact[] contacts = {
            new Contact("Семенов Иван ", "8912232122", "", "", "", ""),
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
