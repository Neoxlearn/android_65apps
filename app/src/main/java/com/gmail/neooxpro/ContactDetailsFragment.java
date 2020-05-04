package com.gmail.neooxpro;
/* Формирование View деталей контакта и заполнение его полей из полученных аргументов*/

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;

public class ContactDetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contact_details_fragment, container, false);
        MainActivity.toolB.setTitle("Детали контакта");
        int position = (int) this.getArguments().getLong("args");
        MainActivity.contactService.getContactDetailsById(view, position);

        return view;
    }




}
