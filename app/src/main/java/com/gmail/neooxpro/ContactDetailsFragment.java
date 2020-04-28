package com.gmail.neooxpro;
/* Формирование View деталей контакта и заполнение его полей из полученных аргументов*/

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ContactDetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contact_details_fragment, container, false);
        MainActivity.toolB.setTitle("Детали контакта");
        int position = (int) this.getArguments().getLong("args");

        TextView contactName = view.findViewById(R.id.contactName);
        TextView contactPhone = view.findViewById(R.id.contactPhone);
        TextView contactPhone2 = view.findViewById(R.id.contactPhone2);
        TextView contactEmail1 = view.findViewById(R.id.contactMail_1);
        TextView contactEmail2 = view.findViewById(R.id.contactMail_2);
        TextView contactDescription = view.findViewById(R.id.contactDescription);

        contactName.setText(Contact.contacts[position].getName());
        contactPhone.setText(Contact.contacts[position].getPhone());
        contactPhone2.setText(Contact.contacts[position].getPhone2());
        contactEmail1.setText(Contact.contacts[position].getEmail1());
        contactEmail2.setText(Contact.contacts[position].getEmail2());
        contactDescription.setText(Contact.contacts[position].getDescription());

        return view;
    }




}
