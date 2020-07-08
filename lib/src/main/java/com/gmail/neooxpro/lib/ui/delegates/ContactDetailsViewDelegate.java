package com.gmail.neooxpro.lib.ui.delegates;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.gmail.neooxpro.java.domain.model.Contact;
import com.gmail.neooxpro.lib.R;
import com.gmail.neooxpro.lib.ui.viewmodel.ContactDetailsViewModel;

public class ContactDetailsViewDelegate {

    private TextView contactName;
    private TextView contactPhone;
    private TextView contactPhone2;
    private TextView contactEmail1;
    private TextView contactEmail2;
    private TextView contactDescription;
    private TextView contactBirthday;
    private Button birthButton;
    private final ProgressBar progressBar;
    private final ContactDetailsViewModel model;
    private final FragmentActivity fragmentActivity;

    public ContactDetailsViewDelegate(@NonNull View view,
                                      @NonNull FragmentActivity fragmentActivity,
                                      @NonNull ContactDetailsViewModel model) {
        contactName = view.findViewById(R.id.contactName);
        contactPhone = view.findViewById(R.id.contactPhone);
        contactPhone2 = view.findViewById(R.id.contactPhone2);
        contactEmail1 = view.findViewById(R.id.contactMail_1);
        contactEmail2 = view.findViewById(R.id.contactMail_2);
        contactDescription = view.findViewById(R.id.contactDescription);
        contactBirthday = view.findViewById(R.id.contactBirthday);
        birthButton = view.findViewById(R.id.birthDayButton);
        progressBar = view.findViewById(R.id.progress_bar_contactDetails);
        this.fragmentActivity = fragmentActivity;
        this.model = model;
    }

    public void setProgressBar(@NonNull LifecycleOwner owner) {
        LiveData<Boolean> progressBarStatus = model.isLoading();
        progressBarStatus.observe(owner,
                isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));
    }

    public void setViews(@NonNull Contact contact, @NonNull LifecycleOwner owner) {
        contactName.setText(contact.getName());
        contactPhone.setText(contact.getPhone());
        contactPhone2.setText(contact.getPhone2());
        contactEmail1.setText(contact.getEmail1());
        contactEmail2.setText(contact.getEmail2());
        contactDescription.setText(contact.getDescription());
        contactBirthday.setText(String.format(fragmentActivity.getString(R.string.bTitle),
                contact.getBirthdayDate()));

        if (contact.getBirthday() != null) {
            notificationProcessing(contact, owner);
        } else {
            birthButton.setText(R.string.noBirthdayDate);
        }
    }

    @NonNull
    public ContactDetailsViewModel getModel() {
        return model;
    }

    private void notificationProcessing(@NonNull final Contact contact, @NonNull LifecycleOwner owner) {
        LiveData<Boolean> haveNotification = model.getNotificationStatus();
        haveNotification.observe(owner, this::setBirthButtonText);

        birthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getModel().enableOrDisableBirthdayNotification(contact);
            }
        });

    }

    private void setBirthButtonText(boolean haveNotification) {
        if (haveNotification) {
            birthButton.setText(R.string.notificationOn);
        } else {
            birthButton.setText(R.string.notificationOff);
        }
    }

    public void onDestroyView() {
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
