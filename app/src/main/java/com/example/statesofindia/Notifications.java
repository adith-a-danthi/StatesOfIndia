package com.example.statesofindia;

import android.app.NotificationManager;
import android.content.Context;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Notifications {

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;

    private void getDailyNotification(Context context){
        mNotifyManager = (NotificationManager)
                context.getApplicationContext()
                        .getSystemService(NOTIFICATION_SERVICE);

    }

}
