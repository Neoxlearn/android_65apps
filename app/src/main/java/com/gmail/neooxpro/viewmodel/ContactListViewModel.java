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

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ContactListViewModel extends AndroidViewModel {
    private IssueRepository repository;
    private final CompositeDisposable compositeDisposable;
    private MutableLiveData<ArrayList<Contact>> contactList;
    private MutableLiveData<Boolean> loading;

    public ContactListViewModel(@NonNull Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
        repository = new ContactsResolver();
        if (loading == null) {
            loading = new MutableLiveData<>();
        }
        if (contactList == null) {
            contactList = new MutableLiveData<>();
        }

    }

    public LiveData<ArrayList<Contact>> getData(String name) {
        loadData(name);
        return contactList;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    @SuppressLint("CheckResult")
    private void loadData(String name) {
       compositeDisposable.add(repository.loadContactList(getApplication(), name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> loading.setValue(true))
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
