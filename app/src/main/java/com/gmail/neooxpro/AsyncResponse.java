package com.gmail.neooxpro;

import java.lang.ref.WeakReference;

public interface AsyncResponse {
    void processFinish(WeakReference<Contact[]> contactsWeak);
}
