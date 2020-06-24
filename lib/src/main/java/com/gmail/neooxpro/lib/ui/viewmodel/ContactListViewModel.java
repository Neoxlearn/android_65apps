package com.gmail.neooxpro.lib.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.gmail.neooxpro.java.domain.interactor.ContactsInteractor;
import com.gmail.neooxpro.java.domain.model.Contact;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ContactListViewModel extends AndroidViewModel {

    @NonNull
    private final ContactsInteractor interactor;
    private final CompositeDisposable compositeDisposable;
    private MutableLiveData<ArrayList<Contact>> contactList;
    private MutableLiveData<Boolean> loading;
    private PublishSubject<String> subject;

    @Inject
    public ContactListViewModel(@NonNull Application application, @NonNull ContactsInteractor interactor) {
        super(application);
        this.interactor = interactor;
        subject = PublishSubject.create();
        compositeDisposable = new CompositeDisposable();
        if (loading == null) {
            loading = new MutableLiveData<>();
        }
        if (contactList == null) {
            contactList = new MutableLiveData<>();
        }
        initialize();


    }

    public LiveData<ArrayList<Contact>> getData() {
        return contactList;
    }

    public void setSubject(String name){
        subject.onNext(name);
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    private void initialize(){
        compositeDisposable.add(subject.switchMapSingle(query -> interactor.getContactList(query).subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> loading.setValue(true))
                .subscribe(contacts -> {
                            contactList.setValue(contacts);
                            loading.setValue(false);
                        },
                        throwable -> {
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
