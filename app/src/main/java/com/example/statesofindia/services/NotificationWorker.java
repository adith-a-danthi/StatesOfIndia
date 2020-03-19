package com.example.statesofindia.services;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.statesofindia.Notifications;
import com.example.statesofindia.StateRepository;
import com.example.statesofindia.data.State;

public class NotificationWorker extends Worker {

    private StateRepository stateRepository;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        stateRepository = StateRepository.getRepository((Application) context.getApplicationContext());
    }

    //Task to be performed periodically is mentioned here
    @NonNull
    @Override
    public Result doWork() {
        State state = stateRepository.getRandomState();
        Notifications.getDailyNotification(getApplicationContext(), state);
        return Result.success();
    }
}
