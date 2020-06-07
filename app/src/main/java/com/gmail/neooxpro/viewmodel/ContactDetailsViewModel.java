package com.gmail.neooxpro.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gmail.neooxpro.model.Contact;
import com.gmail.neooxpro.repo.IssueRepository;
import com.gmail.neooxpro.service.ContactsResolver;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class ContactDetailsViewModel extends AndroidViewModel {

    private static final String TAG = "contactDetailsError";
    private IssueRepository repository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Contact> contact;
    private MutableLiveData<Boolean> loading;

    public ContactDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactsResolver();
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

        compositeDisposable.add(repository.findContactById(id, getApplication())
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
