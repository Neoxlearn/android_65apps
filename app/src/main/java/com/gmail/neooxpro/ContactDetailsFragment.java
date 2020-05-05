package com.gmail.neooxpro;
/* Формирование View деталей контакта и заполнение его полей из полученных аргументов*/

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;


public class ContactDetailsFragment extends Fragment implements AsyncResponseContactDetails{
    private ContactService getContactService = null;
    private TextView contactName;
    private TextView contactPhone;
    private TextView contactPhone2;
    private TextView contactEmail1;
    private TextView contactEmail2;
    private TextView contactDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contact_details_fragment, container, false);
        MainActivity.toolB.setTitle("Детали контакта");
        int id = (int) this.getArguments().getLong("args");
        contactName = view.findViewById(R.id.contactName);
        contactPhone = view.findViewById(R.id.contactPhone);
        contactPhone2 = view.findViewById(R.id.contactPhone2);
        contactEmail1 = view.findViewById(R.id.contactMail_1);
        contactEmail2 = view.findViewById(R.id.contactMail_2);
        contactDescription = view.findViewById(R.id.contactDescription);
        AsyncResponseContactDetails asyncResponse = this;
        getContactService.getContactDetailsById(asyncResponse, id);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GetContactService) {
            getContactService = ((GetContactService) context).contactServiceForFragment();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getContactService = null;
    }

    @Override
    public void processFinish(Contact contact) {
        contactName.setText(contact.getName());
        contactPhone.setText(contact.getPhone());
        contactPhone2.setText(contact.getPhone2());
        contactEmail1.setText(contact.getEmail1());
        contactEmail2.setText(contact.getEmail2());
        contactDescription.setText(contact.getDescription());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contactName = null;
        contactPhone = null;
        contactPhone2 = null;
        contactEmail1 = null;
        contactEmail2 = null;
        contactDescription = null;
    }

}
