package com.example.statesofindia;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.statesofindia.data.State;
import com.example.statesofindia.ui.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Notifications {

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;

    public static void getDailyNotification(Context context, State state){
        mNotifyManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        //Creating notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel primaryChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Daily Quiz", NotificationManager.IMPORTANCE_LOW);
            primaryChannel.enableLights(true);
            primaryChannel.setLightColor(Color.CYAN);
            mNotifyManager.createNotificationChannel(primaryChannel);
        }

        //To switch to app when notification is clicked
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(context,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Notification to be displayed
        String notificationTitle = "Daily Quiz Time";
        String notificationContent = "What is the capital of " + state.getStateName() + " ?";
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context,PRIMARY_CHANNEL_ID)
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true);

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }



}
