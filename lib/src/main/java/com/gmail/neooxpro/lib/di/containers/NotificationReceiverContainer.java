package com.gmail.neooxpro.lib.di.containers;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.lib.service.NotificationReceiver;

public interface NotificationReceiverContainer {
    void inject(@NonNull NotificationReceiver notificationReceiver);
}
