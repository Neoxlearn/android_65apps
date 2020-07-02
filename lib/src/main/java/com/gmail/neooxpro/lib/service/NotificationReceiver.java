package com.gmail.neooxpro.lib.service;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.gmail.neooxpro.java.domain.interactor.BirthdayNotificationInteractor;
import com.gmail.neooxpro.java.domain.repo.BirthdayNotificationRepository;
import com.gmail.neooxpro.java.domain.repo.CalendarRepository;
import com.gmail.neooxpro.lib.di.app.HasAppContainer;
import com.gmail.neooxpro.lib.di.containers.NotificationReceiverContainer;
import com.gmail.neooxpro.lib.ui.MainActivity;
import com.gmail.neooxpro.lib.R;

import java.util.Calendar;

import javax.inject.Inject;


public class NotificationReceiver extends BroadcastReceiver {
    @Inject
    CalendarRepository calendarRepository;
    @Inject
    BirthdayNotificationRepository bDayRepository;
    @Inject
    BirthdayNotificationInteractor bDayInteractor;

    private static final String CHANNEL_ID = "My channel" ;

    @Override
    public void onReceive(Context context, Intent intent) {
        Application app = ((Application) context.getApplicationContext());
        if (!(app instanceof HasAppContainer)) {
            throw new IllegalStateException();
        }
        NotificationReceiverContainer notificationReceiverContainer = ((HasAppContainer)app).appContainer()
                .plusNotificationReceiverContainer();
        notificationReceiverContainer.inject(this);
        createNotificationChannel(context);
        final String id = intent.getStringExtra("id");
        if (id != null) {
            String message = intent.getStringExtra("message");
            String contactName = intent.getStringExtra("name");
            Intent resultIntent = new Intent(context, MainActivity.class);
            resultIntent.putExtra("id", id);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(id.hashCode(), builder.build());
            repeatAlarm(id, contactName);
        }

    }


    private void createNotificationChannel(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void repeatAlarm(String id, String name){
        Calendar birthday = calendarRepository.getNow();
        bDayRepository.closeAlarm(id);
        bDayInteractor.enableOrDisableBirthdayNotification(id, name, birthday);
    }


}
