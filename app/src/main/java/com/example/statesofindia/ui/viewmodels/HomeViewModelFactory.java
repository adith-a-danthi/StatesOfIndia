package com.example.statesofindia.ui.viewmodels;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.ExecutionException;

public class HomeViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private Application application;
    private Integer x;
    public HomeViewModelFactory(@NonNull Application application, Integer x) {
        super(application);
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new HomeViewModel(application, x);
    }
}
