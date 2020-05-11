package com.gmail.neooxpro;
/* Формирование View деталей контакта и заполнение его полей из полученных аргументов*/

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import androidx.fragment.app.Fragment;

import java.util.Calendar;


public class ContactDetailsFragment extends Fragment implements AsyncResponseContactDetails{
    private ContactService getContactService = null;
    private TextView contactName;
    private TextView contactPhone;
    private TextView contactPhone2;
    private TextView contactEmail1;
    private TextView contactEmail2;
    private TextView contactDescription;
    private TextView contactBirthday;
    private AlarmManager alarmMgr;
    private Intent intent;
    private Button birthButton;
    private static final String ALARM_ACTION = "com.gmail.neooxpro.alarm";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contact_details_fragment, container, false);
        MainActivity.toolB.setTitle(R.string.contactDetails);
        int id = (int) this.getArguments().getLong("args");
        contactName = view.findViewById(R.id.contactName);
        contactPhone = view.findViewById(R.id.contactPhone);
        contactPhone2 = view.findViewById(R.id.contactPhone2);
        contactEmail1 = view.findViewById(R.id.contactMail_1);
        contactEmail2 = view.findViewById(R.id.contactMail_2);
        contactDescription = view.findViewById(R.id.contactDescription);
        contactBirthday = view.findViewById(R.id.contactBirthday);
        birthButton = view.findViewById(R.id.birthDayButton);
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


    @SuppressLint("SetTextI18n")
    @Override
    public void processFinish(Contact contact) {
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

    }

    public void notificationProcessing(final Contact contact){
        final int id = contact.getId();
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
        intent.putExtra("id",  id);
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

    }

}
