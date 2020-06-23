package com.gmail.neooxpro.lib.ui.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gmail.neooxpro.java.domain.interactor.ContactDetailsInterator;
import com.gmail.neooxpro.java.domain.interactor.ContactsInteractor;
import com.gmail.neooxpro.java.domain.model.Contact;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class ContactDetailsViewModel extends AndroidViewModel {

    @NonNull
    private final ContactDetailsInterator interactor;
    private final CompositeDisposable compositeDisposable;
    private MutableLiveData<Contact> contact;
    private MutableLiveData<Boolean> loading;

    @Inject
    public ContactDetailsViewModel(@NonNull Application application, @NonNull ContactDetailsInterator interactor) {
        super(application);
        this.interactor = interactor;
        compositeDisposable = new CompositeDisposable();
        if (loading == null) {
            loading = new MutableLiveData<>();
        }
    }

    public LiveData<Contact> getData(String id) {
        loadData(id);
        return contact;
    }

    public LiveData<Boolean> isLoading() {

        return loading;
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
                }, throwable ->{
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
