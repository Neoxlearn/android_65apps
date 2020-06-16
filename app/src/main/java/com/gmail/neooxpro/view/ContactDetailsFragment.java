package com.gmail.neooxpro.view;
/* Формирование View деталей контакта и заполнение его полей из полученных аргументов*/

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.gmail.neooxpro.map.ContactMapFragment;
import com.gmail.neooxpro.model.Contact;
import com.gmail.neooxpro.FragmentListener;
import com.gmail.neooxpro.service.NotificationReceiver;
import com.gmail.neooxpro.R;
import com.gmail.neooxpro.viewmodel.ContactDetailsViewModel;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ContactDetailsFragment extends DaggerFragment {
    private TextView contactName;
    private TextView contactPhone;
    private TextView contactPhone2;
    private TextView contactEmail1;
    private TextView contactEmail2;
    private TextView contactDescription;
    private TextView contactBirthday;
    private ProgressBar progressBar;
    private AlarmManager alarmMgr;
    private Intent intent;
    private Button birthButton;
    private static final String ALARM_ACTION = "com.gmail.neooxpro.alarm";
    private static final int REQUEST_CODE = 1;
    private String contact_id;
    private Toolbar toolbar;

    @Inject
    ViewModelProvider.Factory factory;
    ContactDetailsViewModel model;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this, factory).get(ContactDetailsViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.contact_details_fragment, container, false);
        progressBar = view.findViewById(R.id.progress_bar_contactDetails);
        toolbar.setTitle(R.string.contactDetails);
        assert this.getArguments() != null;
        contact_id =  this.getArguments().getString("args");
        contactName = view.findViewById(R.id.contactName);
        contactPhone = view.findViewById(R.id.contactPhone);
        contactPhone2 = view.findViewById(R.id.contactPhone2);
        contactEmail1 = view.findViewById(R.id.contactMail_1);
        contactEmail2 = view.findViewById(R.id.contactMail_2);
        contactDescription = view.findViewById(R.id.contactDescription);
        contactBirthday = view.findViewById(R.id.contactBirthday);
        birthButton = view.findViewById(R.id.birthDayButton);

        if(requireContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
        }
        else {
            queryContactDetails(contact_id);
        }
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                queryContactDetails(contact_id);
            } else
                Toast.makeText(requireContext(),R.string.noPermissions, Toast.LENGTH_LONG).show();
        }
    }

    public void queryContactDetails(String id){
        LiveData<Boolean> progressBarStatus = model.isLoading();
        LiveData<Contact> data = model.getData(id);
        progressBarStatus.observe(getViewLifecycleOwner(),
                isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));
        data.observe(getViewLifecycleOwner(), contact -> {
            contactName.setText(contact.getName());
            contactPhone.setText(contact.getPhone());
            contactPhone2.setText(contact.getPhone2());
            contactEmail1.setText(contact.getEmail1());
            contactEmail2.setText(contact.getEmail2());
            contactDescription.setText(contact.getDescription());

            contactBirthday.setText(String.format(getString(R.string.bTitle), contact.getBirthdayDate()));
            if (contact.getBirthday() != null)
                notificationProcessing(contact);
            else birthButton.setText(R.string.noBirthdayDate);
        });
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_details, menu);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener){
            toolbar = ((FragmentListener) context).getToolbar();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        toolbar = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        model = null;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_item:
                openContactMapFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openContactMapFragment(){

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ContactMapFragment cdf = new ContactMapFragment();
            Bundle bundle = new Bundle();
            bundle.putString("args", contact_id);
            cdf.setArguments(bundle);
            ft
                    .replace(R.id.container, cdf)
                    .addToBackStack(null)
                    .commit();

    }

    public void notificationProcessing(final Contact contact){
        final int id = Integer.parseInt(contact.getId());
        intent = new Intent(ALARM_ACTION);

        intent.setClass(requireContext(), NotificationReceiver.class);
        alarmMgr = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

        if (checkAlarm(id))
            setBirthButtonText(false);
        else
            setBirthButtonText(true);

        birthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAlarm(id)) {
                    makeAlarm(contact, id);
                } else {
                    closeAlarm(id);
                }
           }
        });
    }



    private void setBirthButtonText(boolean haveNotification){
        if (haveNotification){
            birthButton.setText(R.string.notificationOn);
        } else {
            birthButton.setText(R.string.notificationOff);
        }
    }

    private void makeAlarm(Contact contact, int id){
        Calendar birthday = contact.getBirthday();
        checkDate(birthday);
        intent.putExtra("id",  contact.getId());
        intent.putExtra("message", String.format(getString(R.string.birthdayToday), contact.getName()));
        PendingIntent alarmIntent = PendingIntent.getBroadcast(requireContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, contact.getBirthday().getTimeInMillis(), DateUtils.YEAR_IN_MILLIS, alarmIntent);
        setBirthButtonText(true);
    }

    private void checkDate(Calendar birthday){
        Calendar calendar = Calendar.getInstance();
        int curMonth = calendar.get(Calendar.MONTH);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        if (curMonth > birthday.get(Calendar.MONTH) || (curMonth == birthday.get(Calendar.MONTH) && curDay > birthday.get(Calendar.DAY_OF_MONTH)))
        {
            birthday.add(Calendar.YEAR, 1);
        }
    }

    private void closeAlarm(int id){
        PendingIntent alarmIntent = PendingIntent.getBroadcast(requireContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.cancel(alarmIntent);
        alarmIntent.cancel();
        setBirthButtonText(false);
    }


    private boolean checkAlarm(int id){
        return (PendingIntent.getBroadcast(requireContext().getApplicationContext(), id, intent,
                PendingIntent.FLAG_NO_CREATE ) == null);

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
        contactBirthday = null;
        birthButton = null;
        progressBar = null;

    }

}
