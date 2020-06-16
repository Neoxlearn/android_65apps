package com.gmail.neooxpro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.gmail.neooxpro.model.Contact;
import com.gmail.neooxpro.repo.IssueRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ContactListViewModel extends AndroidViewModel {

    IssueRepository repository;
    private final CompositeDisposable compositeDisposable;
    private MutableLiveData<ArrayList<Contact>> contactList;
    private MutableLiveData<Boolean> loading;
    private PublishSubject<String> subject;

    @Inject
    public ContactListViewModel(@NonNull Application application, IssueRepository repository) {
        super(application);
        this.repository = repository;
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
        compositeDisposable.add(subject.switchMapSingle(query -> repository.loadContactList(getApplication(), query).subscribeOn(Schedulers.io()))
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
        repository = null;
    }
}
