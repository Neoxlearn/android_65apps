package com.gmail.neooxpro;
/* Формирование View деталей контакта и заполнение его полей из полученных аргументов*/

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ContactDetailsFragment extends Fragment {
    private ContactService getContactService = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contact_details_fragment, container, false);
        MainActivity.toolB.setTitle("Детали контакта");
        int id = (int) this.getArguments().getLong("args");

        TextView contactName = view.findViewById(R.id.contactName);
        TextView contactPhone = view.findViewById(R.id.contactPhone);
        TextView contactPhone2 = view.findViewById(R.id.contactPhone2);
        TextView contactEmail1 = view.findViewById(R.id.contactMail_1);
        TextView contactEmail2 = view.findViewById(R.id.contactMail_2);
        TextView contactDescription = view.findViewById(R.id.contactDescription);

        Contact contact = getContactService.getContactDetailsById(id);
        contactName.setText(contact.getName());
        contactPhone.setText(contact.getPhone());
        contactPhone2.setText(contact.getPhone2());
        contactEmail1.setText(contact.getEmail1());
        contactEmail2.setText(contact.getEmail2());
        contactDescription.setText(contact.getDescription());

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getContactService = ((GetContactService) activity).contactServiceForFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getContactService = null;
    }
}
