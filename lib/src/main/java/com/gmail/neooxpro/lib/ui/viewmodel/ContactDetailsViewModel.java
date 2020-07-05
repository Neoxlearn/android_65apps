package com.gmail.neooxpro.lib.ui.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gmail.neooxpro.java.domain.interactor.BirthdayNotificationInteractor;
import com.gmail.neooxpro.java.domain.interactor.ContactDetailsInterator;
import com.gmail.neooxpro.java.domain.model.Contact;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class ContactDetailsViewModel extends AndroidViewModel {

    @NonNull
    private final ContactDetailsInterator interactor;
    private final CompositeDisposable compositeDisposable;
    private final BirthdayNotificationInteractor bDayInteractor;
    private MutableLiveData<Contact> contact;
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<Boolean> haveBdayNotification;

    @Inject
    public ContactDetailsViewModel(@NonNull Application application,
                                   @NonNull BirthdayNotificationInteractor bDayInteractor,
                                   @NonNull ContactDetailsInterator interactor) {
        super(application);
        this.interactor = interactor;
        this.bDayInteractor = bDayInteractor;
        compositeDisposable = new CompositeDisposable();
        if (loading == null) {
            loading = new MutableLiveData<>();
        }
        if (haveBdayNotification == null) {
            haveBdayNotification = new MutableLiveData<>();
        }

    }

    @NonNull
    public LiveData<Contact> getData(@NonNull String id) {
        loadData(id);
        return contact;
    }

    @NonNull
    public LiveData<Boolean> isLoading() {
        return loading;
    }

    @NonNull
    public LiveData<Boolean> getNotificationStatus() {
        return haveBdayNotification;
    }

    public void haveNotification(@NonNull String id) {
        haveBdayNotification.setValue(!bDayInteractor.checkAlarm(id));
    }

    public void enableOrDisableBirthdayNotification(@NonNull Contact contact) {
        bDayInteractor.enableOrDisableBirthdayNotification(contact.getId(), contact.getName(), contact.getBirthday());
        haveNotification(contact.getId());
    }

    @SuppressLint("CheckResult")
    private void loadData(String id) {
        if (contact == null) {
            contact = new MutableLiveData<>();
        }

        compositeDisposable.add(interactor.getContactById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> loading.setValue(true))
                .subscribe(contact -> {
                    this.contact.setValue(contact);
                    loading.setValue(false);
                }, throwable -> {
                    throwable.getStackTrace();
                    loading.setValue(false);
                }));

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

}
