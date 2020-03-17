package com.example.statesofindia;

import android.app.NotificationManager;
import android.content.Context;

import com.example.statesofindia.data.State;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Notifications {

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;

    public static void getDailyNotification(Context context, State state){
        mNotifyManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

    }



}
